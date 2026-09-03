package vn.iotstar.config;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;

/**
 * Lớp test cấu hình JPA (Hibernate) với MySQL.
 * Chạy main để kiểm tra việc tạo bảng và thêm dữ liệu mẫu (Category, Video, User).
 */
public class Test {

    public static void main(String[] args) {
        EntityManager enma = JpaConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        try {
            trans.begin();

            // 1. Thêm User mẫu nếu chưa có
            TypedQuery<User> userQuery = enma.createQuery("SELECT u FROM User u WHERE u.username = :uname", User.class);
            userQuery.setParameter("uname", "admin");
            List<User> existingUsers = userQuery.getResultList();

            if (existingUsers.isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("123456");
                admin.setFullname("Quản trị viên");
                admin.setEmail("admin@iotstar.vn");
                admin.setRoleid(1);
                enma.persist(admin);
                System.out.println("-> Da tao user mau: admin / 123456");
            }

            // 2. Thêm Category mẫu nếu chưa có
            TypedQuery<Category> cateQuery = enma.createQuery("SELECT c FROM Category c", Category.class);
            List<Category> existingCates = cateQuery.getResultList();

            if (existingCates.isEmpty()) {
                Category cate = new Category();
                cate.setCategoryname("Iphone");
                cate.setImages("abc.jpg");
                cate.setStatus(1);
                enma.persist(cate);

                Video video = new Video();
                video.setVideoId("v01");
                video.setTitle("test");
                video.setActive(true);
                video.setCategory(cate);
                enma.persist(video);
                System.out.println("-> Da tao category mau: Iphone");
            }

            trans.commit();
            System.out.println("Them du lieu mau thanh cong!");
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
        } finally {
            enma.close();
            System.exit(0);
        }
    }
}
