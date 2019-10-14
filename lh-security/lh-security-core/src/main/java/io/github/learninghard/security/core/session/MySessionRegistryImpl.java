package io.github.learninghard.security.core.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-27
 * \* Time: 14:05
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Slf4j
@Component
public class MySessionRegistryImpl implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {
    /**
     * 声明两个key分别对应 SessionRegistryImpl 中的两个 ConcurrentHashMap
     * @see SessionRegistryImpl
     * key:用户名 value:SessionId
     */
    private static final String PRINCIPALS = "PRINCIPALS";
    /**
     * 声明两个key分别对应 SessionRegistryImpl 中的两个 ConcurrentHashMap
     * @see SessionRegistryImpl
     * key:SessionId value:用户对象
     */
    private static final String SESSIONIDS = "SESSIONIDS";

    /**
     * Redis模板对象
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        String sessionId = event.getId();
        this.removeSessionInformation(sessionId);
    }

    /**
     * @return
     */
    @Override
    public List<Object> getAllPrincipals() {
        return new ArrayList(this.getPrincipalsKeySet());
    }

    /**
     * @param principal
     * @param includeExpiredSessions
     * @return
     */
    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        Set<String> sessionsUsedByPrincipal = (Set) this.getPrincipals(((User) principal).toString());
        if (sessionsUsedByPrincipal == null) {
            return Collections.emptyList();
        } else {
            List<SessionInformation> list = new ArrayList(sessionsUsedByPrincipal.size());
            Iterator var5 = sessionsUsedByPrincipal.iterator();

            while (true) {
                SessionInformation sessionInformation;
                do {
                    do {
                        if (!var5.hasNext()) {
                            return list;
                        }
                        String sessionId = (String) var5.next();
                        sessionInformation = this.getSessionInformation(sessionId);
                    } while (sessionInformation == null);
                } while (!includeExpiredSessions && sessionInformation.isExpired());

                list.add(sessionInformation);
            }
        }
    }

    /**
     * 获取session信息
     *
     * @param sessionId
     * @return
     */
    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        return (SessionInformation) this.getSessionIds(sessionId);
    }

    /**
     * 根据sessionId刷新
     *
     * @param sessionId
     */
    @Override
    public void refreshLastRequest(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            info.refreshLastRequest();
            // 根据sessionId保存是刷新后的SessionInformation信息
            this.putSessionIds(sessionId,info);
        }
    }

    /**
     * 注册新缓存
     * @param sessionId
     * @param principal
     */
    @Override
    public void registerNewSession(String sessionId, Object principal) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        Assert.notNull(principal, "Principal required as per interface contract");
        if (log.isDebugEnabled()) {
            log.debug("Registering session " + sessionId + ", for principal " + principal);
        }

        if (this.getSessionInformation(sessionId) != null) {
            this.removeSessionInformation(sessionId);
        }

        this.putSessionIds(sessionId, new SessionInformation(principal, sessionId, new Date()));
        /**
         * 下面的与注释等同
         * Set<String> sessionsUsedByPrincipal = (Set)this.principals.computeIfAbsent(principal, (key) -> {
         *   return new CopyOnWriteArraySet();
         * });
         */
        Set<String> sessionsUsedByPrincipal = (Set) this.getPrincipals(principal.toString());
        if (sessionsUsedByPrincipal == null) {
            sessionsUsedByPrincipal = new CopyOnWriteArraySet();
        }
        sessionsUsedByPrincipal.add(sessionId);
        this.putPrincipals(principal.toString(), sessionsUsedByPrincipal);

        if (log.isTraceEnabled()) {
            log.trace("Sessions used by '" + principal + "' : " + sessionsUsedByPrincipal);
        }

    }



    @Override
    public void removeSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            if (log.isTraceEnabled()) {
                log.debug("Removing session " + sessionId + " from set of registered sessions");
            }

            this.removeSessionIds(sessionId);
            Set<String> sessionsUsedByPrincipal = (Set) this.getPrincipals(((User) info.getPrincipal()).toString());
            if (sessionsUsedByPrincipal != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Removing session " + sessionId + " from principal's set of registered sessions");
                }

                sessionsUsedByPrincipal.remove(sessionId);
                if (sessionsUsedByPrincipal.isEmpty()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Removing principal " + info.getPrincipal() + " from registry");
                    }

                    this.removePrincipals(((User) info.getPrincipal()).toString());
                }

                if (log.isTraceEnabled()) {
                    log.trace("Sessions used by '" + info.getPrincipal() + "' : " + sessionsUsedByPrincipal);
                }

            }
        }
    }

    /**
     * redis 添加 SESSIONIDS
     * @param sessionId
     * @param sessionInformation
     */
    void putSessionIds(String sessionId, SessionInformation sessionInformation) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
        log.info("保存sessionId为{}",sessionId);
        hashOperations.put(sessionId, sessionInformation);
    }

    /**
     * redis 获取 SESSIONIDS
     * @param sessionId
     */
    private Object getSessionIds(String sessionId) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
        return hashOperations.get(sessionId);
    }

    /**
     * redis 删除 SESSIONIDS
     * @param sessionId
     */
    private Long removeSessionIds(String sessionId) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(SESSIONIDS);
        return hashOperations.delete(sessionId);
    }

    /**
     * 根据键取值
     * @param principal
     * @return
     */
    private Object getPrincipals(String principal) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        return hashOperations.get(principal);
    }

    /**
     * 获取键的集合
     * @return
     */
    private Set<String> getPrincipalsKeySet(){
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        return hashOperations.keys();
    }

    /**
     * 删除
     * @param principal
     * @return
     */
    private Long removePrincipals(String principal){
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        return hashOperations.delete(principal);
    }

    /**
     * 保存并返回保存在redis中的值
     * @param principal
     * @param sessionsUsedByPrincipal
     * @return
     */
    private Object putIfAbsentPrincipals(String principal, Set<String> sessionsUsedByPrincipal) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        hashOperations.putIfAbsent(principal, sessionsUsedByPrincipal);
        return hashOperations.get(principal);
    }

    private void putPrincipals(String principal, Set<String> sessionsUsedByPrincipal) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(PRINCIPALS);
        hashOperations.put(principal, sessionsUsedByPrincipal);
    }
}
