package app.controller;

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
    private VideoCardRepo videoCardRepo;
    @Autowired
    private ParserServiceImpl parserService;

    @GetMapping("/")
    public String main(Model model) {
        videoCardRepo.deleteAll();
//        parserService.start();
        model.addAttribute("videocards", videoCardRepo.findAll());
        return "main";
    }
    @GetMapping("/catalog")
    public String catalog() {
        return "catalog";
    }
}
