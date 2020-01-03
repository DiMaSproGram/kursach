package app.controller;

import app.UserInSystem;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
//    @Autowired
//    private VideoCardService videoCardService;
//    @Autowired
//    private ProcessorService processorService;
//    @Autowired
//    private MotherboardService motherboardService;
//    @Autowired
//    private HDDService hddService;
//    @Autowired
//    private RAMService ramService;
//    @Autowired
//    private SSDService ssdService;
//    @Autowired
//    private PowerSupplyService powerSupplyService;
//    @Autowired
//    private CoolerService coolerService;
//    @Autowired
//    private CompCaseService compCaseService;
    @Autowired
    private HardwareService hardwareService;
    @Autowired
    private UserInSystem userInSystem;

    @GetMapping
    public String catalog(Model model) {
        model.addAttribute("active", userInSystem.isActive());
        return "catalog";
    }

    @GetMapping("/video_cards")
    public String videocards(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("video_card"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/processors")
    public String processors(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("processor"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/motherboards")
    public String motherboards(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("motherboard"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/hdd")
    public String hdd(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("hdd"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/ram")
    public String ram(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("ram"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/ssd")
    public String ssd(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("ssd"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/power_supplies")
    public String powersupply(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("power_supply"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/coolers")
    public String cooler(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("coolers"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
    @GetMapping("/computer_cases")
    public String compcase(Model model) {
        model.addAttribute("hardwares", hardwareService.getAllByType("computer_case"));
        model.addAttribute("active", userInSystem.isActive());
        return "hardware";
    }
}
