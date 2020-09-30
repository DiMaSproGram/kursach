package app.controller;

import app.entity.User;
import app.service.impl.ParserService;
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
        model.addAttribute("active", user != null);
        return "main";
    }
}
