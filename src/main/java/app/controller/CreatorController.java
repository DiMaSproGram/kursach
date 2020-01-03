package app.controller;

import app.UserInSystem;

import app.entity.Hardware;
import app.service.*;
import app.service.impl.CreatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class CreatorController {
    @Autowired
    public AssemblyService assemblyService;
    @Autowired
    public CreatorServiceImpl creatorService;
    @Autowired
    public UserService userService;
    @Autowired
    public UserInSystem userInSystem;


    private ArrayList<Hardware> hardwareList;
    private double totalPrice;

    @GetMapping("/creator")
    public String creator(Model model) {
        hardwareList = new ArrayList<>();
        totalPrice = 0;
        model.addAttribute("active", userInSystem.isActive());
        return "pccreator";
    }
    @PostMapping("/creator/pc")
    public String create(@RequestParam double price, @RequestParam String goal, Model model) {
        if ( price > 6100) {
            model.addAttribute("active", userInSystem.isActive());
            model.addAttribute("error","неправильно указана цена (больше чем 6100)");
            return "pccreator";
        }
        double temp = 0;
        hardwareList = creatorService.create(price, goal);
        for (Hardware hardware : hardwareList)
            temp += hardware.getPrice();
        totalPrice = temp;
        model.addAttribute("hardwares", hardwareList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("active", userInSystem.isActive());
        return "pccreator";
    }
    @PostMapping("/creator/save")
    public String save(Model model) {
        Hardware[] arr = new Hardware[hardwareList.size()];
        for(int i = 0; i < hardwareList.size(); ++i) {
            arr[i] = hardwareList.get(i);
            System.out.println(arr[i]);
        }

        assemblyService.save(arr, userService.findUserByUsername(userInSystem.getUserName()), totalPrice);
        model.addAttribute("active", userInSystem.isActive());
        return "pccreator";
    }
}
