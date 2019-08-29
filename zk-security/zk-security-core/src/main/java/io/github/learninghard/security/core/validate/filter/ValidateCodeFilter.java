package io.github.learninghard.security.core.validate.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 15:24
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ValidateCodeFilter extends OncePerRequestFilter {/* 一次请求只通过一次filter，而不需要重复执行 */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


    }
}
