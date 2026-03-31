package kata.spring_boot_app.service;

import kata.spring_boot_app.model.User;
import kata.spring_boot_app.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserDao userDao;
    private final BCryptPasswordEncoder coder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder coder) {
        this.userDao = userDao;
        this.coder = coder;
    }


    @Override
    public void addUser(User user) {
        user.setPassword(coder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public List<User> allUsers() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(findById(id));
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("not found"));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Not found"));
    }
}
