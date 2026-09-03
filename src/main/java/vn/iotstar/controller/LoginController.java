package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI();

        // Xử lý ĐĂNG XUẤT (/logout)
        if (uri.contains("/logout")) {
            // 1. Hủy Session trên Server
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.removeAttribute("account");
                session.invalidate();
            }

            // 2. Xóa Cookie ghi nhớ trên Client
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        cookie.setMaxAge(0);
                        cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                        resp.addCookie(cookie);
                    }
                }
            }

            resp.sendRedirect(req.getContextPath() + "/login?message=logged_out");
            return;
        }

        // Nếu đã đăng nhập qua Session, chuyển hướng thẳng vào /admin/categories
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }

        // Xử lý ĐỌC COOKIE khi mở form login
        Cookie[] cookies = req.getCookies();
        String savedUsername = "";
        boolean isRemember = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    savedUsername = cookie.getValue();
                    isRemember = true;
                    break;
                }
            }
        }

        req.setAttribute("savedUsername", savedUsername);
        req.setAttribute("isRemember", isRemember);
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        // Kiểm tra thông tin đăng nhập từ CSDL
        User user = userService.login(username, password);

        if (user != null) {
            // ==========================================
            // 1. XỬ LÝ LOGIN VỚI SESSION (Lưu trên Server)
            // ==========================================
            HttpSession session = req.getSession(true);
            session.setAttribute("account", user);

            // ==========================================
            // 2. XỬ LÝ LOGIN VỚI COOKIE (Lưu trên Client)
            // ==========================================
            String cookiePath = req.getContextPath().isEmpty() ? "/" : req.getContextPath();
            if ("on".equals(remember) || "true".equals(remember)) {
                // Người dùng chọn Remember Me: Tạo Cookie lưu username trong 7 ngày
                Cookie cookieUser = new Cookie("username", username);
                cookieUser.setMaxAge(7 * 24 * 60 * 60);
                cookieUser.setPath(cookiePath);
                resp.addCookie(cookieUser);
            } else {
                // Không chọn Remember Me: Xóa cookie cũ nếu có
                Cookie cookieUser = new Cookie("username", "");
                cookieUser.setMaxAge(0);
                cookieUser.setPath(cookiePath);
                resp.addCookie(cookieUser);
            }

            // Đăng nhập thành công -> chuyển hướng sang danh sách Category
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else {
            // Đăng nhập thất bại
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            req.setAttribute("savedUsername", username);
            req.setAttribute("isRemember", "on".equals(remember));
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
