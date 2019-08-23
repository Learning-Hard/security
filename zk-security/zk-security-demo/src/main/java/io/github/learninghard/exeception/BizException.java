package io.github.learninghard.exeception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-23
 * \* Time: 9:51
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 自定异常处理
 * \
 * @author Learning Hard
 */
@ControllerAdvice
@ResponseBody
public class BizException {

    Logger logger = LoggerFactory.getLogger(BizException.class);

    @ExceptionHandler(ResourceNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map processException(ResourceNotExistException exception){
        logger.info("自定义错误处理类");
        Map map = new HashMap();
        map.put("exception","服务器内部错误");
        map.put("id",exception.getId());
        return map;
    }

}
