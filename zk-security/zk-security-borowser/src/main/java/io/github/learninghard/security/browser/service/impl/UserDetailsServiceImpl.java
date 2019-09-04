package io.github.learninghard.security.browser.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-28
 * \* Time: 17:58
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 自定义认证逻辑,登陆数据的存取,指定加密方式,是否校验过期,黑名单等
 * \
 * @author Learning-Hard
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 加密算法*/
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * TODO: 修改说明
         * 1、加密算法可以去掉，后期直接从数据库或者缓存取用户的密码和权限信息然后返回
         * 2、返回User对象后期可以支持过期，冻结，等功能
         */

        logger.info("登陆用户为" + username);
        logger.info("登陆用户密码为：" + passwordEncoder.encode("123456"));

        /**
         * TODO 后期改为查询后台数据返回
         */
        return new User(username,passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

    }
}
