package kata.spring_boot_app.repository;

import kata.spring_boot_app.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    void deleteUser(Long id);

    List<User> allUsers();

    User findById(Long id);
}
