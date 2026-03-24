package kata.spring_boot_app.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kata.spring_boot_app.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public List<User> allUsers() {
        return em.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
