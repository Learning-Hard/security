package io.github.learninghard.security.core.social.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-23
 * \* Time: 15:02
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class MyConnectView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        if (model.get("connections") != null) {
            response.getWriter().print("<h2>绑定成功</h2>");
        } else {
            response.getWriter().print("<h2>解绑成功</h2>");
        }
    }
}
