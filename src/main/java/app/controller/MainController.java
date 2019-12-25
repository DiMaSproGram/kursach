package app.controller;

import app.UserInSystem;
import app.repository.VideoCardRepo;
import app.service.ParserService;
import app.service.impl.ParserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private ParserServiceImpl parserService;
    @Autowired
    private UserInSystem userInSystem;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("active", userInSystem.isActive());
        return "main";
    }

}
