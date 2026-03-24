package kata.spring_boot_app.controller;

import kata.spring_boot_app.model.User;
import kata.spring_boot_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = service.allUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String name, @RequestParam String surname, @RequestParam String email) {
        User user = new User(name, surname, email);
        service.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String editUserForm(@RequestParam Long id, Model model) {
        User user = service.findById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam Long id,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String email) {
        User user = new User(name, surname, email);
        user.setId(id);
        service.addUser(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        service.deleteUser(id);
        return "redirect:/users";
    }
}