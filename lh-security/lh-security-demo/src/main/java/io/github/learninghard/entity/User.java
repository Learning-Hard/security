package io.github.learninghard.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-07-20
 * \* Time: 17:06
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 用户实体
 * \
 */
@Data
@Entity
@Table(name = "zk_user")
public class User {

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(generator = "idWorker")
    @GenericGenerator(name = "idWorker", strategy = "io.github.learninghard.utils.IdWorker")

    private Long userId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    //    @MyConstraint
    private String nickname;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Past(message = "生日必须是过去的时间")
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public Long getUserId() {
        return userId;
    }

    @JsonView(UserSimpleView.class)
    public String getNickname() {
        return nickname;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public User(){
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
}