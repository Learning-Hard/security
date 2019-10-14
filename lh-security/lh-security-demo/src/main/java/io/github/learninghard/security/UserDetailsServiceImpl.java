package io.github.learninghard.security;

import io.github.learninghard.dao.UserRepository;
import io.github.learninghard.entity.User;
import io.github.learninghard.security.core.social.provider.SocialUserPlus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-28
 * \* Time: 17:58
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 自定义认证逻辑,登陆数据的存取,指定加密方式,是否校验过期,黑名单等
 * \
 *
 * @author Learning-Hard
 */
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登陆登陆用户为" + username);
        return buildUser(username);
    }

    /**
     * 三方登陆实现
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登陆用户ID为" + userId);
        return buildUser(userId);

    }

    private SocialUserDetails buildUser(String username) {

        /**
         * TODO: 修改说明
         * 1、加密算法可以去掉，后期直接从数据库或者缓存取用户的密码和权限信息然后返回
         * 2、返回User对象后期可以支持过期，冻结，等功能
         */
        User user = userRepository.findByUsername(username);
        log.info("当前登陆用户为{}", ReflectionToStringBuilder.toString(user, ToStringStyle.SIMPLE_STYLE));
        if (user != null) {
            /**
             * TODO 后期改为查询后台数据返回
             */
            /**
             * Username必须是唯一的
             */
            return new SocialUserPlus(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        }
        //TODO 抛一个用户不存在异常
        return null;
    }

}
