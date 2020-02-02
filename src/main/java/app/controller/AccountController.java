package app.controller;

import app.UserInSystem;
import app.entity.Assembly;
import app.entity.Hardware;
import app.service.AssemblyService;
import app.service.HardwareService;
import app.service.UserService;
import com.api2pdf.client.Api2PdfClient;
import com.api2pdf.models.Api2PdfResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AssemblyService assemblyService;
    @Autowired
    HardwareService hardwareService;
    @Autowired
    UserInSystem userInSystem;
    @Autowired
    UserService userService;

    private Api2PdfClient api2PdfClient = new Api2PdfClient("e3fefcd4-f652-4709-ba2e-ede5e09560e1");


    @GetMapping
    public String creator(Model model) {
        ArrayList<ArrayList<Hardware>> assemblyList = assemblyService.getByUser(userService.findUserByUsername(userInSystem.getUserName()).getId());
        model.addAttribute("user", userInSystem.getUserName());
        model.addAttribute("active", userInSystem.isActive());
        model.addAttribute("assembles", assemblyList);
        return "account";
    }
    @PostMapping("/pdf")
    public String convertToPdf(Model model) throws IOException {
        URL bhv = new URL("http://localhost:8080/account");
        BufferedReader br = new BufferedReader(new InputStreamReader(bhv.openStream(),"UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String temp;

        while((temp = br.readLine())!= null)
            stringBuilder.append(temp);
        br.close();
        Api2PdfResponse api2PdfResponse = api2PdfClient.wkhtmlToPdfFromHtml(stringBuilder.toString(), true, "accountInfo");
        model.addAttribute("pdfLink", api2PdfResponse.getPdf());
        return "pdfLink";
    }
    @PostMapping("/exit")
    public String exit(Model model){
        userInSystem.setActive(false);
        userInSystem.setUserName("");

        return "redirect:/";
    }
}
