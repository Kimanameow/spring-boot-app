package kata.spring_boot_app.repository;

import kata.spring_boot_app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
