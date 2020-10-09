package app.controller;

import app.entity.Role;
import app.entity.User;
import app.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    public final ParserService parseService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user, Model model) {
        if(user != null && user.getRoles().contains(Role.ADMIN)) {
            return "redirect:http://localhost:3000";
        }
        model.addAttribute("active", user != null);
        return "main";
    }
}
