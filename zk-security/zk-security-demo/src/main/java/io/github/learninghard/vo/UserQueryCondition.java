package io.github.learninghard.vo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-07-21
 * \* Time: 0:10
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 查询条件对象
 * \
 */
public class UserQueryCondition {
    private String username;
    private String age;
    private int ageTo;
    private String xxx;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }

    public UserQueryCondition(String username, String age, int ageTo, String xxx) {
        this.username = username;
        this.age = age;
        this.ageTo = ageTo;
        this.xxx = xxx;
    }
}
