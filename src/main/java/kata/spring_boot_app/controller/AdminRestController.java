package kata.spring_boot_app.controller;

import kata.spring_boot_app.Mapper;
import kata.spring_boot_app.dto.UserDTO;
import kata.spring_boot_app.model.Role;
import kata.spring_boot_app.model.User;
import kata.spring_boot_app.repository.RoleDao;
import kata.spring_boot_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public AdminRestController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users.stream().map(Mapper::toUserDto).toList());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user,
                                              @RequestParam(required = false) List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            user.setRoles(new HashSet<>(roleDao.findAllById(roleIds)));
        } else {
            user.setRoles(new HashSet<>());
        }
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.toUserDto(user));
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return user != null ? ResponseEntity.ok(Mapper.toUserDto(user))
                : ResponseEntity.notFound().build();
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user,
                                              @RequestParam(required = false) List<Long> roleIds,
                                              @PathVariable Long id) {
        User existingUser = userService.findById(id);
        Set<Role> roles;
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (roleIds != null) {
                roles = new HashSet<>(roleDao.findAllById(roleIds));
            } else {
                roles = new HashSet<>();
            }
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setEmail(user.getEmail());

            if (!roles.isEmpty()) {
                existingUser.setRoles(roles);
            } else {
                existingUser.setRoles(new HashSet<>());
            }
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
                userService.updateUser(existingUser);
            } else {
                userService.updateUser(existingUser);
            }
        }
        return ResponseEntity.ok(Mapper.toUserDto(existingUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}