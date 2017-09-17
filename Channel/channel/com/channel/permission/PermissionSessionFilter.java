package com.channel.permission;

import com.channel.model.user.CXtUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 25019 on 2015/12/18.
 */
public class PermissionSessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            //取的url相对地址，例如：/Channel/index.jsp
            String url = httpRequest.getRequestURI();
            if (url == null)
                return;

            //静态文件直接过滤
            if (url.contains("/img/") ||/*所有img中的文件全部放行*/
                    url.contains("/common/") ||/*所有common中的文件全部放行*/
                    url.endsWith(".js") ||/*所有.js文件全部放行*/
                    url.endsWith(".css") ||
                    url.contains("/outinterface/")) {/*所有css文件全部放行*/
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            //拦截非登录相关的接口
            if (!url.contains("login") && !url.contains("verifyCode")) {//增加验证码过滤 change by 庄佳彬 at 2017-03-28
//            if (!url.contains("login")) {
/*                AttributePrincipal principal = (AttributePrincipal) httpRequest.getUserPrincipal();
                String account = principal.getName();
                if (account == null || "".equals(account)) {
                    String path = httpRequest.getContextPath();
                    String basePath = httpRequest.getScheme() + "://"
                            + httpRequest.getServerName() + ":" + httpRequest.getServerPort()
                            + path + "/";
                    httpResponse.sendRedirect(basePath);
                    return;
                }*/
                HttpSession session = httpRequest.getSession();
                CXtUser user = (CXtUser) session.getAttribute("user");

                if (user == null) {
                    String path = httpRequest.getContextPath();
                    String basePath = httpRequest.getScheme() + "://"
                            + httpRequest.getServerName() + ":" + httpRequest.getServerPort()
                            + path + "/";
                    httpResponse.sendRedirect(basePath + "page/login/login.jsp");
                    return;
                }
            }
        } catch (Exception e) {
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
