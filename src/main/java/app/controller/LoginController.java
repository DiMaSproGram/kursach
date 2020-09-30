package app.controller;

import app.entity.User;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("message", "Неправильное имя пользователя или пароль");
        }
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/registration")
    public String registration( Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(User user, Model model) {
        return userService.register(user, model)
            ? "redirect:/login"
            : "/registration";
    }
}
