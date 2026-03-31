package kata.spring_boot_app.service;

import kata.spring_boot_app.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void addUser(User user);

    List<User> allUsers();

    User findById(Long id);

    void deleteUser(Long id);

    User findByEmail(String email);
}
