package app.controller;

import app.entity.HardwareEntity;
import app.entity.User;
import app.payload.Feature;
import app.payload.Hardware;
import app.payload.InputRanges;
import app.service.AssemblyService;
import app.service.CreatorService;
import app.service.HardwareFeatureService;
import app.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;


@Controller
@RequiredArgsConstructor
public class CreatorController {

  public final AssemblyService assemblyService;
  public final HardwareFeatureService hardwareFeatureService;
  public final CreatorService creatorService;
  public final UserService userService;

  private ArrayList<HardwareEntity> hardwareEntityList;
  private ArrayList<Integer> featureNumList = new ArrayList<>();
  private ArrayList<Feature> featuresList = new ArrayList<>();
  private ArrayList<Hardware.Feature> featureNameList = new ArrayList<>(
      Arrays.asList(
          Hardware.Feature.CORE_AMOUNT,
          Hardware.Feature.CLOCK_RATE,
          Hardware.Feature.VIDEO_MEMORY,
          Hardware.Feature.VOLUME_RAM,
          Hardware.Feature.VOLUME_HDD,
          Hardware.Feature.VOLUME_SSD
      )
  );

  private double totalPrice;
  private double price;
  private String goal = "1";
  private boolean isSSD;

  @GetMapping("/creator")
  public String creator(@AuthenticationPrincipal User user, Model model) {
    hardwareEntityList = new ArrayList<>();
    totalPrice = 0;
    model.addAttribute("goal", goal);
    model.addAttribute("active", user != null);
    model.addAttribute("hardwares", hardwareEntityList);
    model.addAttribute("totalPrice", 0);
    return "pccreator";
  }


  @PostMapping("/creator/pc")
  public String create(
      @ModelAttribute("range") InputRanges range,
      @RequestParam double price,
      @RequestParam String goal,
      @RequestParam String isSSD,
      @AuthenticationPrincipal User user,
      Model model
  ) throws JsonProcessingException {
    this.price = price;
    this.goal = goal;
    this.isSSD = Boolean.parseBoolean(isSSD);

//        hardwareEntityList.clear();

    hardwareEntityList = creatorService.create(
        price,
        goal,
        Boolean.parseBoolean(isSSD)
    );

    totalPrice = hardwareEntityList.stream().mapToDouble(HardwareEntity::getPrice).sum();

    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);

    featuresList.clear();
    featureNumList.clear();

    hardwareFeatureService.fillFeatureNameListAndFeaturesList(
        featureNameList,
        featuresList
    );
    hardwareFeatureService.fillFeatureNumList(
        hardwareEntityList,
        featureNameList,
        featureNumList,
        featuresList
    );

    model.addAttribute("arr", featuresList);
    model.addAttribute("arr2", featureNumList);
    model.addAttribute("json", new ObjectMapper().writeValueAsString(featuresList));
    model.addAttribute("price", price);
    model.addAttribute("goal", goal);
    model.addAttribute("isSSD", isSSD);
    model.addAttribute("hardwares", hardwareEntityList);
    model.addAttribute("totalPrice", df.format(totalPrice));
    model.addAttribute("active", user != null);

    return "pc";
  }

  @PostMapping("/creator/rebuild")
  public String create(@ModelAttribute("range") InputRanges range, @AuthenticationPrincipal User user, Model model) throws JsonProcessingException {
    double temp = 0;
    hardwareEntityList = creatorService.create(
        price,
        goal,
        isSSD,
        new ArrayList<>(
            Arrays.asList(
                range.core_amount,
                range.clock_rate,
                range.video_memory,
                range.volume_ram,
                range.volume_hdd,
                range.volume_ssd
            )
        ),
        featureNumList,
        hardwareEntityList,
        featureNameList,
        featuresList
    );

    totalPrice = hardwareEntityList.stream().mapToDouble(HardwareEntity::getPrice).sum();

    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);

    featuresList.clear();
    featureNumList.clear();

    hardwareFeatureService.fillFeatureNameListAndFeaturesList(
        featureNameList,
        featuresList
    );
    hardwareFeatureService.fillFeatureNumList(
        hardwareEntityList,
        featureNameList,
        featureNumList,
        featuresList
    );

    model.addAttribute("arr", featuresList);
    model.addAttribute("arr2", featureNumList);
    model.addAttribute("json", new ObjectMapper().writeValueAsString(featuresList));
    model.addAttribute("price", price);
    model.addAttribute("goal", goal);
    model.addAttribute("isSSD", isSSD);
    model.addAttribute("hardwares", hardwareEntityList);
    model.addAttribute("totalPrice", df.format(totalPrice));
    model.addAttribute("active", user != null);

    return "pc";
  }

  @PostMapping("/creator/save")
  public String save(@AuthenticationPrincipal User user, Model model) throws JsonProcessingException {
    HardwareEntity[] arr = hardwareEntityList.toArray(new HardwareEntity[0]);

    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);

    assemblyService.save(
        arr,
        userService.loadUserByUsername(user.getUsername()),
        Double.parseDouble(df.format(totalPrice))
    );

    totalPrice = hardwareEntityList.stream().mapToDouble(HardwareEntity::getPrice).sum();

    featuresList.clear();
    featureNumList.clear();

    hardwareFeatureService.fillFeatureNameListAndFeaturesList(
        featureNameList,
        featuresList
    );
    hardwareFeatureService.fillFeatureNumList(
        hardwareEntityList,
        featureNameList,
        featureNumList,
        featuresList
    );

    model.addAttribute("arr", featuresList);
    model.addAttribute("arr2", featureNumList);
    model.addAttribute("json", new ObjectMapper().writeValueAsString(featuresList));
    model.addAttribute("price", price);
    model.addAttribute("goal", goal);
    model.addAttribute("isSSD", isSSD);
    model.addAttribute("hardwares", hardwareEntityList);
    model.addAttribute("totalPrice", df.format(totalPrice));
    model.addAttribute("active", user != null);
    return "pc";
  }


}
