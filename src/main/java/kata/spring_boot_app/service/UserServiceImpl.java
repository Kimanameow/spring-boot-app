package kata.spring_boot_app.service;

import kata.spring_boot_app.model.User;
import kata.spring_boot_app.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public List<User> allUsers() {
        return userDao.allUsers();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
}
