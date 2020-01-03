package app.controller;

import app.UserInSystem;
import app.entity.User;
import app.repository.UserRepo;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInSystem userInSystem;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findUserByUsername(username);
        if(user == null) {
            model.addAttribute("message", "Пользователя с таким ником не существует");
            return "login";
        }
        if(user.getPassword() != password.hashCode()){
            model.addAttribute("message", "Неправильно набран пароль");
            return "login";
        }
        userInSystem.setActive(true);
        userInSystem.setUserName(username);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findUserByUsername(username);

        if(user != null) {
            model.addAttribute("message", "Пользователь уже зарегистрирован");
            return "registration";
        }
        userService.save(new User(username, password.hashCode()));

        return "redirect:/login";
    }
}
