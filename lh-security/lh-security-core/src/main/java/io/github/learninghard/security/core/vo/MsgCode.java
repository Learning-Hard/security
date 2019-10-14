package io.github.learninghard.security.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 16:31
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 服务请求结果描述
 * \
 */
@Getter
@AllArgsConstructor
public enum MsgCode {
    DEFAULT("SUCCESS", "处理成功"),
    FAILURE("FAILURE", "处理失败");

    private final String key;
    private final String desc;
}
