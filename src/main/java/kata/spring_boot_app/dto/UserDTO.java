package kata.spring_boot_app.dto;

import kata.spring_boot_app.model.Role;
import lombok.Setter;

import java.util.Set;

@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String surname;
    private Set<Role> roles;
}
