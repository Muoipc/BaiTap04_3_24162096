package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.util.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = {"/profile", "/admin/profile"})
public class ProfileController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=need_login");
            return;
        }

        User sessionUser = (User) session.getAttribute("account");
        User user = userService.findById(sessionUser.getId());
        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/profile.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=need_login");
            return;
        }

        User sessionUser = (User) session.getAttribute("account");
        User user = userService.findById(sessionUser.getId());

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        // Xử lý upload file ảnh đại diện bằng Multipart
        String uploadPath = Constant.DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            Part part = req.getPart("imageFile");
            if (part != null && part.getSize() > 0) {
                String originalFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                int dotIndex = originalFileName.lastIndexOf(".");
                String extension = (dotIndex >= 0) ? originalFileName.substring(dotIndex) : ".png";
                String newFileName = "user_" + System.currentTimeMillis() + extension;

                part.write(uploadPath + File.separator + newFileName);
                user.setImages(newFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cập nhật các thông tin cá nhân
        user.setFullname(fullname);
        user.setPhone(phone);

        // Gọi JPA update
        userService.update(user);

        // Cập nhật lại session để hiển thị tên mới nhất trên giao diện
        session.setAttribute("account", user);

        resp.setContentType("text/html;charset=UTF-8");
        req.setAttribute("user", user);
        req.setAttribute("message", "Cập nhật hồ sơ thành công!");
        req.getRequestDispatcher("/views/profile.jsp").include(req, resp);
    }
}
