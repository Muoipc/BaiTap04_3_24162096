package vn.iotstar.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(255) NOT NULL")
    private String password;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(255) NULL")
    private String fullname;

    @Column(name = "phone", columnDefinition = "VARCHAR(20) NULL")
    private String phone;

    @Column(name = "email", columnDefinition = "VARCHAR(150) NULL")
    private String email;

    @Column(name = "images", columnDefinition = "VARCHAR(255) NULL")
    private String images;

    @Column(name = "avatar", columnDefinition = "VARCHAR(255) NULL")
    private String avatar;

    @Column(name = "roleid")
    private int roleid; // 1: Admin, 2: User

    public User() {
    }

    public User(String username, String password, String fullname, String email, int roleid) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.roleid = roleid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImages() {
        return images != null ? images : avatar;
    }

    public void setImages(String images) {
        this.images = images;
        this.avatar = images;
    }

    public String getAvatar() {
        return avatar != null ? avatar : images;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        this.images = avatar;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }
}
