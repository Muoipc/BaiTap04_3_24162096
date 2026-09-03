package vn.iotstar.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filter kiểm tra quyền truy cập các đường dẫn /admin/*
 * Yêu cầu người dùng phải có Session đăng nhập hợp lệ ("account").
 */
@WebFilter(urlPatterns = {"/admin/*", "/profile*", "/user/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("account") != null);

        if (isLoggedIn) {
            // Đã đăng nhập qua Session -> cho phép đi tiếp
            chain.doFilter(request, response);
        } else {
            // Chưa đăng nhập -> Chuyển hướng về trang đăng nhập kèm thông báo
            resp.sendRedirect(req.getContextPath() + "/login?error=need_login");
        }
    }

    @Override
    public void destroy() {
    }
}
