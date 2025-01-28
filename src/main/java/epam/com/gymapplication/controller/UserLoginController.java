package epam.com.gymapplication.controller;

import epam.com.gymapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserLoginController {
    private final UserService userService;


    public UserLoginController(UserService userService) {
        this.userService = userService;


    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/auth/user")
    public String authenticateTheUser() {
        return "redirect:/home";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }




}
