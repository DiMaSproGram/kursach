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
    public String main() {
//        videoCardRepo.deleteAll();
//        parserService.start();
        return "main";
    }

}
