package io.github.learninghard.security.core.vo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 16:32
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 服务请求状态编码
 * \
 */
public enum StatusCode {
    DEFAULT("0000", "数据处理正确"),
    CODE_1000("1000", "请求参数错误"),
    CODE_2000("2000", "服务降级错误"),
    CODE_3000("3000", "系统错误（网络连接错误/超时）"),
    CODE_3001("3001", "网络连接错误"),
    CODE_3002("3002", "网络连接超时"),
    CODE_4000("4000", "权限异常");

    private final String key;
    private final String desc;

    public String getKey() {
        return this.key;
    }

    public String getDesc() {
        return this.desc;
    }

    private StatusCode(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
