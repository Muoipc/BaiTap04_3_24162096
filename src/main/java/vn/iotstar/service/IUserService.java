package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.User;

public interface IUserService {

    User login(String username, String password);

    User findByUsername(String username);

    User findById(int id);

    void insert(User user);

    void update(User user);

    List<User> findAll();
}
