package app.controller;

import app.entity.Role;
import app.entity.User;
import app.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserServiceImpl userService;

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("active", user != null);
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("");
        User userFromDb = userService.loadUserByUsername(username);
        if(userFromDb == null) {
            model.addAttribute("message", "Пользователя с таким ником не существует");
            return "login";
        }
        if(!userFromDb.getPassword().equals(Integer.toString(password   .hashCode()))){
            model.addAttribute("message", "Неправильно набран пароль");
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("active", user != null);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = userService.loadUserByUsername(user.getUsername());

        if(userFromDb != null) {
            model.addAttribute("message", "Пользователь уже зарегистрирован");
            return "registration";
        }
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.USER));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);

        return "redirect:/login";
    }
}
