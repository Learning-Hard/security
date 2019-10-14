package io.github.learninghard.support;

import io.github.learninghard.entity.User;
import io.github.learninghard.security.core.properties.SecurityConstants;
import io.github.learninghard.service.impl.UserServiceImpl;
import io.github.learninghard.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-20
 * \* Time: 9:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 完成未登陆用户自动注册
 * \
 *
 * @author Learning-Hard
 */
//@Component
public class ConnectionSignUpImpl implements ConnectionSignUp {

    @Autowired
    UserServiceImpl userService;

    /**
     * 返回注册对象的userId
     *
     * @param connection
     * @return
     */
    @Override
    public String execute(Connection<?> connection) {
        String username = "user_" + new IdWorker().nextId();
        User user = new User(username, SecurityConstants.DEFAULT_AUTO_REGISTER_USER_PASSWORD);
        user.setNickname(connection.getDisplayName());
        /**
         * 注册新用户为默认密码
         * TODO 登陆后校验密码为初始密码时,重定向到修改密码页面
         */
        userService.save(user);
        return username;
    }
}
