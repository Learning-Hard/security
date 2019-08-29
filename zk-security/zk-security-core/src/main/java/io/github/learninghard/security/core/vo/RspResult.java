package io.github.learninghard.security.core.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 16:27
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 请求响应结果
 * \
 */
public class RspResult {
    private String RSP_CODE;
    private String RSP_DESC;
    private Object DATA;
    private Object ATTACH;

    public RspResult() {
        this.RSP_CODE = StatusCode.DEFAULT.getKey();
        this.RSP_DESC = MsgCode.DEFAULT.getKey();
    }

    public RspResult(String RSP_CODE, String RSP_DESC, Object DATA) {
        this.RSP_CODE = (StringUtils.isEmpty(RSP_CODE) ? StatusCode.DEFAULT.getKey() : RSP_CODE);
        this.RSP_DESC = (StringUtils.isEmpty(RSP_DESC) ? MsgCode.DEFAULT.getKey() : RSP_DESC);
        this.DATA = DATA;
    }

    public String getRSP_CODE() {
        return this.RSP_CODE;
    }

    public void setRSP_CODE(String RSP_CODE) {
        this.RSP_CODE = RSP_CODE;
    }

    public String getRSP_DESC() {
        return this.RSP_DESC;
    }

    public void setRSP_DESC(String RSP_DESC) {
        this.RSP_DESC = RSP_DESC;
    }

    public Object getDATA() {
        return this.DATA;
    }

    public void setDATA(Object DATA) {
        this.DATA = DATA;
    }

    public Object getATTACH() {
        return this.ATTACH;
    }

    public void setATTACH(Object ATTACH) {
        this.ATTACH = ATTACH;
    }
}
