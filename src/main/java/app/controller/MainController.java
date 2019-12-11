package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "main";
    }
    @GetMapping("/catalog")
    public String catalog() {
        return "catalog";
    }
}
