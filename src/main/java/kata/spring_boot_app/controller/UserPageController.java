package kata.spring_boot_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
}