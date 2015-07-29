package com.lamfire.crawler.console;

import com.lamfire.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-29
 * Time: 下午12:05
 * To change this template use File | Settings | File Templates.
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        Object admin = request.getSession().getAttribute("ADMIN");

        String servletPath = request.getServletPath();

        if(admin != null || StringUtils.contains(servletPath,"login") || StringUtils.contains(servletPath,"captcha")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.sendRedirect(request.getContextPath() +"/login.jsp");
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
