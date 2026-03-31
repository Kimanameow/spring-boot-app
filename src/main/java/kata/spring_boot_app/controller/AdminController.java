package kata.spring_boot_app.controller;

import kata.spring_boot_app.model.Role;
import kata.spring_boot_app.model.User;
import kata.spring_boot_app.repository.RoleDao;
import kata.spring_boot_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/users";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleDao.findAll());
        return "admin/add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam("roleIds") List<Long> roleIds) {
        List<Role> roles = roleDao.findAllById(roleIds);
        user.setRoles(new HashSet<>(roles));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleDao.findAll());
        return "admin/edit-user";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user, @RequestParam("roleIds") List<Long> roleIds) {
        User existingUser = userService.findById(user.getId());
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());

        List<Role> roles = roleDao.findAllById(roleIds);
        existingUser.setRoles(new HashSet<>(roles));

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
            userService.addUser(existingUser);
        } else {
            userService.addUser(existingUser);
        }

        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}