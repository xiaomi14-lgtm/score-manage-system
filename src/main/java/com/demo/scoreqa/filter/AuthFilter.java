package com.demo.scoreqa.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录认证过滤器：未登录用户访问受保护资源时重定向到登录页。
 * 放行路径通过 init-param excludedPaths 配置（逗号分隔）。
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = "/*",
        initParams = @WebInitParam(name = "excludedPaths",
                value = "/login.jsp,/loginServlet,/logoutServlet,/css,/js,/images,/favicon.ico"))
public class AuthFilter implements Filter {

    private String[] excludedPaths = new String[0];

    @Override
    public void init(FilterConfig filterConfig) {
        String paths = filterConfig.getInitParameter("excludedPaths");
        if (paths != null && !paths.trim().isEmpty()) {
            excludedPaths = paths.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(contextPath.length());

        // 放行静态资源与登录相关路径
        if (isExcluded(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loginUser") != null) {
            chain.doFilter(request, response);
            return;
        }

        // 未登录：重定向到登录页
        resp.sendRedirect(contextPath + "/login.jsp");
    }

    private boolean isExcluded(String path) {
        for (String excluded : excludedPaths) {
            String p = excluded.trim();
            if (p.isEmpty()) {
                continue;
            }
            // 精确匹配，或作为目录前缀匹配（如 /css 匹配 /css/style.css），
            // 避免 startsWith(p) 把 /cssfoo 之类误判为放行
            if (path.equals(p) || path.startsWith(p + "/")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        // nothing to do
    }
}
