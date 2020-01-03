package app.controller;

import app.UserInSystem;
import app.entity.Assembly;
import app.entity.Hardware;
import app.service.AssemblyService;
import app.service.HardwareService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class AccountController {
    @Autowired
    AssemblyService assemblyService;
    @Autowired
    HardwareService hardwareService;
    @Autowired
    UserInSystem userInSystem;
    @Autowired
    UserService userService;

    @GetMapping("/account")
    public String creator(Model model) {
        ArrayList<Assembly> assemblyList = (ArrayList<Assembly>) assemblyService.getByUser(userService.findUserByUsername(userInSystem.getUserName()).getId());
        ArrayList<ArrayList<Hardware>> resList = new ArrayList<>();
        ArrayList<Double> totalPriceList = new ArrayList<>();

        for (Assembly assembly : assemblyList) {
            resList.add(new ArrayList<>(Arrays.asList(assembly.getHardware())));
            totalPriceList.add(assembly.getTotalPrice());
        }
        for (ArrayList<Hardware> a : resList){
            System.out.println(a);
        }
        model.addAttribute("user", userInSystem.getUserName());
        model.addAttribute("active", userInSystem.isActive());
        model.addAttribute("assembles", resList);
        model.addAttribute("prices", totalPriceList);
        return "account";
    }
}
