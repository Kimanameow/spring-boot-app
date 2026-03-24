package kata.spring_boot_app.service;

import kata.spring_boot_app.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> allUsers();

    User findById(Long id);

    void deleteUser(Long id);
}
