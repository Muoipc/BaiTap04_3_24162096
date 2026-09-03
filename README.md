# BaiTap04_3_24162096

**Trường Đại học Sư phạm Kỹ thuật TP.HCM (HCMUTE)**  
**Môn học:** Lập trình Web  
**Sinh viên thực hiện:** Nguyễn Song Hoàng Phúc  
**Mã số sinh viên (MSSV):** 24162096  
**Thời gian hoàn thành:** Trước 17:30 ngày 03/09/2026  

---

## Đề tài bài tập
> **Yêu cầu:** Xây dựng chức năng profile của User để update: `fullname`, `phone`, `images` (có sử dụng upload file bằng multipart) bằng **JPA** với quản lý giao diện là **SiteMesh**.

---

## 1. Công nghệ sử dụng
- **Ngôn ngữ & Nền tảng:** Java 17, Jakarta EE 10 / Servlet 6.0
- **Web Server:** Apache Tomcat 11.0.x
- **ORM / JPA:** Hibernate ORM 6.6.18 (Jakarta Persistence 3.1)
- **Quản lý Layout:** SiteMesh 3.3.0-RC1 (`org.sitemesh:sitemesh`)
- **Cơ sở dữ liệu:** MySQL 8.x / Connector 8.3
- **Công cụ xây dựng:** Apache Maven (đóng gói WAR)

---

## 2. Kiến trúc & Tính năng chi tiết

### A. Quản lý Giao diện bằng SiteMesh (`sitemesh`)
- **Filter SiteMesh 3:** `vn.iotstar.filter.MySiteMeshFilter` kế thừa `ConfigurableSiteMeshFilter`.
- **Cấu hình Layout:**
  - `decorators/web.jsp`: Decorator layout cho phía người dùng (`/profile*`, `/user/*`) gồm Header, Menu điều hướng, vùng `<sitemesh:write property="body"/>`, và Footer.
  - `decorators/admin.jsp`: Decorator layout cho khu vực quản trị (`/admin/*`).
  - Loại trừ (exclude) các tài nguyên tĩnh và login/logout: `/image*`, `/login*`, `/logout*`.

### B. Chức năng User Profile (JPA + Multipart Upload)
- **Entity `User.java`:**
  - Ánh xạ bảng `users` qua JPA Annotations (`@Entity`, `@Table`, `@Id`, `@Column`).
  - Đầy đủ các trường: `id`, `username`, `password`, `email`, `fullname`, `phone`, `images`, `roleid`.
- **DAO & Service:**
  - `IUserDao` / `UserDaoImpl`: Thực hiện `findById` và `update(User user)` qua `EntityManager.merge(user)`.
  - `IUserService` / `UserServiceImpl`: Tầng nghiệp vụ xử lý logic tài khoản.
- **Controller `ProfileController.java` (`/profile`):**
  - Khai báo `@MultipartConfig(fileSizeThreshold = 2MB, maxFileSize = 10MB)`.
  - **`doGet`:** Lấy thông tin user từ Session, truy vấn dữ liệu mới nhất bằng JPA và chuyển tiếp hiển thị lên view.
  - **`doPost`:** Đọc dữ liệu `fullname`, `phone`, nhận file upload `imageFile` qua `req.getPart()`, lưu file ảnh vào thư mục upload và cập nhật tên file vào JPA.
  - Tự động cập nhật `HttpSession` để thanh header và avatar hiển thị thông tin mới nhất tức thì.

### C. Quản trị Phân quyền (`AuthFilter`)
- Kiểm tra Session đăng nhập (`account`) đối với các đường dẫn `/admin/*` và `/profile*`. Nếu chưa đăng nhập sẽ chuyển hướng về `/login`.

---

## 3. Cấu trúc thư mục Source Code
```
BaiTap04_3_24162096/
├── src/main/java/vn/iotstar/
│   ├── config/
│   │   ├── JpaConfig.java          # Tạo EntityManagerFactory
│   │   └── Test.java               # Seed tài khoản admin & danh mục mẫu
│   ├── entity/
│   │   ├── Category.java           # Entity Danh mục
│   │   ├── Video.java              # Entity Video
│   │   └── User.java               # Entity User (fullname, phone, images)
│   ├── dao/ & dao/impl/            # Tầng truy xuất dữ liệu JPA
│   ├── service/ & service/impl/    # Tầng Service
│   ├── controller/
│   │   ├── CategoryController.java # CRUD Category
│   │   ├── LoginController.java    # Đăng nhập (Session + Cookie)
│   │   ├── ProfileController.java  # Cập nhật Profile (Multipart + JPA)
│   │   └── DownloadImageController.java # Tải & xem ảnh (/image?fname=...)
│   └── filter/
│       ├── AuthFilter.java         # Bảo mật Session
│       └── MySiteMeshFilter.java   # Filter SiteMesh 3 Layout
├── src/main/resources/
│   └── META-INF/persistence.xml    # Cấu hình Hibernate JPA & MySQL
└── src/main/webapp/
    ├── WEB-INF/
    │   ├── web.xml                 # Cấu hình Web App & Filter SiteMesh
    │   └── sitemesh3.xml           # Mapping layout SiteMesh 3
    ├── decorators/
    │   ├── web.jsp                 # Layout người dùng (Header/Nav/Body/Footer)
    │   └── admin.jsp               # Layout quản trị
    └── views/
        ├── login.jsp               # Giao diện đăng nhập
        ├── profile.jsp             # Giao diện cập nhật Profile (User)
        └── admin/                  # Giao diện quản lý Category (CRUD)
```

---

## 4. Hướng dẫn chạy và kiểm thử

### Bước 1: Chuẩn bị CSDL MySQL
- Tạo database: `servletjpa`
- User: `root` | Password: `123456`
- Chạy `Test.java` để tự động sinh bảng và tạo tài khoản mẫu:
  - Tài khoản: `admin` | Mật khẩu: `123456`

### Bước 2: Build và Chạy dự án
```bash
# Biên dịch và đóng gói WAR
mvn clean package

# Copy WAR vào thư mục webapps của Tomcat 11
cp target/BaiTap04_3_24162096.war <TOMCAT_DIR>/webapps/

# Khởi động Tomcat
catalina start
```

### Bước 3: Truy cập trên trình duyệt
- **Đăng nhập:** `http://localhost:8080/BaiTap04_3_24162096/login`
- **Hồ sơ cá nhân:** `http://localhost:8080/BaiTap04_3_24162096/profile`
- **Quản lý Category:** `http://localhost:8080/BaiTap04_3_24162096/admin/categories`

---

## 5. Nộp bài
- **GitHub Repository:** [https://github.com/Muoipc/BaiTap04_3_24162096](https://github.com/Muoipc/BaiTap04_3_24162096)
- **Hệ thống nộp:** UTExLMS
