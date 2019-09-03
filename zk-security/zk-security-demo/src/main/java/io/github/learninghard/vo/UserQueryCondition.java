package io.github.learninghard.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-07-21
 * \* Time: 0:10
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 查询条件对象
 * \
 */
@Data
@ToString
public class UserQueryCondition {
    private String username;
    private String age;
    private int ageTo;
    private String xxx;
}
