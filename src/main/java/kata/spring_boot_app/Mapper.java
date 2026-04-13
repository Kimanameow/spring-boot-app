package kata.spring_boot_app;

import kata.spring_boot_app.dto.UserDTO;
import kata.spring_boot_app.model.User;

public class Mapper {
    public static UserDTO toUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setSurname(user.getSurname());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}
