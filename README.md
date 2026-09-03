# BaiTap04_3_24162096

Bài tập 04_3 - Lập trình Web (HCMUTE) - MSSV: 24162096

CRUD Category và User Profile sử dụng **JPA (Hibernate)** với **Jakarta EE / Servlet 6.0** (Tomcat 11).

## Công nghệ
- Java 17
- Maven (WAR)
- Jakarta Servlet API 6.0 / JSP 3.1 / JSTL 3.0
- Hibernate ORM 6.6 (JPA 3.0 / Jakarta Persistence)
- Hibernate Validator 8.0 (Bean Validation 3.1)
- MySQL (driver 8.3)

## Cấu trúc
```
src/main/java/vn/iotstar
├── config/JpaConfig.java     # Tạo EntityManagerFactory/EntityManager
├── config/Test.java           # Test cấu hình + thêm dữ liệu mẫu
├── entity/Category.java       # Entity Category (1 - N Video)
├── entity/Video.java          # Entity Video
├── dao/ICategoryDao.java      # Interface DAO
├── dao/impl/CategoryDaoImpl.java
├── service/ICategoryService.java
├── service/impl/CategoryServiceImpl.java
├── controller/CategoryController.java       # CRUD Category
├── controller/DownloadImageController.java  # Phục vụ ảnh /image
└── util/Constant.java         # Đường dẫn upload
src/main/resources/META-INF/persistence.xml
src/main/webapp/views/admin/category-*.jsp
```

## Cấu hình
- Database `servletjpa` (MySQL): user `root`, pass `123456`
- Chạy `Test.main` để tạo bảng `categories`, `videos` và thêm dữ liệu mẫu.
- Tự động tạo bảng qua `hibernate.hbm2ddl.auto=update`.

## Chạy
1. `mvn clean package`
2. Nạp WAR vào Tomcat 11 và truy cập `/admin/categories`

## GitHub
- User: Muoipc
- Email: nguyensonghoangphuc0125@gmail.com