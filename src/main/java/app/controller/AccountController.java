package app.controller;

import app.entity.HardwareEntity;
import app.entity.User;
import app.service.AssemblyService;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    public final AssemblyService assemblyService;
    public final UserService userService;

    @GetMapping
    public String account(@AuthenticationPrincipal User user, Model model) {
        HashMap<String, ArrayList<HardwareEntity>> assemblyMap = assemblyService.getByUser(
            userService
                .loadUserByUsername(user.getUsername())
                .getId()
        );

        model.addAttribute("user", user.getUsername());
        model.addAttribute("active", user != null);
        model.addAttribute("assembles", assemblyMap);
        return "account";
    }

}
