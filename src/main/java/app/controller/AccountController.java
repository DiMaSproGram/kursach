package app.controller;

import app.entity.HardwareEntity;
import app.entity.User;
import app.service.AssemblyService;
import app.service.HardwareService;
import app.service.impl.UserServiceImpl;
import com.api2pdf.client.Api2PdfClient;
import com.api2pdf.models.Api2PdfResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.HashMap;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    public final AssemblyService assemblyService;
    public final HardwareService hardwareService;
    public final UserServiceImpl userService;

    private Api2PdfClient api2PdfClient = new Api2PdfClient(
        "e3fefcd4-f652-4709-ba2e-ede5e09560e1"
    );

    @GetMapping
    public String account(@AuthenticationPrincipal User user, Model model) {
        HashMap<String, ArrayList<HardwareEntity>> assemblyMap = assemblyService.getByUser(
            userService.loadUserByUsername(user.getUsername()).getId()
        );

        model.addAttribute("user", user.getUsername());
        model.addAttribute("active", user != null);
        model.addAttribute("assembles", assemblyMap);
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
}
