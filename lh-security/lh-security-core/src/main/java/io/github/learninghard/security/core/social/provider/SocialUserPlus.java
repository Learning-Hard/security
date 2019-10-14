package io.github.learninghard.security.core.social.provider;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-29
 * \* Time: 16:41
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class SocialUserPlus extends SocialUser {

    public SocialUserPlus(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public SocialUserPlus(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public String getUserId() {
        return this.getUsername();
    }

    @Override
    public boolean equals(Object rhs) {
//        return rhs instanceof User ? this.getUsername().equals(((User)rhs).getUsername()) : false;
        return this.getUsername().equals(((User) rhs).getUsername());
    }

    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }

    @Override
    public String toString() {
        return this.getUsername();
    }
}
