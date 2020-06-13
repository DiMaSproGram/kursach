package app.controller;

import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import app.entity.User;
import app.payload.Feature;
import app.payload.Hardware;
import app.payload.InputRanges;
import app.service.*;
import app.service.impl.CreatorServiceImpl;
import app.service.impl.HardwareFeatureService;
import app.service.impl.UserServiceImpl;
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
    public final CreatorServiceImpl creatorService;
    public final UserServiceImpl userService;

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
    public String create(@ModelAttribute("range") InputRanges range, @RequestParam double price, @RequestParam String goal, @RequestParam String isSSD, @AuthenticationPrincipal User user, Model model) throws JsonProcessingException {
        this.price = price;
        this.goal = goal;
        this.isSSD = Boolean.parseBoolean(isSSD);

        double temp = 0;

        hardwareEntityList.clear();

        hardwareEntityList = creatorService.create(
            price,
            goal,
            Boolean.parseBoolean(isSSD)
        );

        for (HardwareEntity hardwareEntity : hardwareEntityList) {
            temp += hardwareEntity.getPrice();
        }
        totalPrice = temp;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        featuresList.clear();
        featureNumList.clear();

        featureNameList.forEach(feature -> {
            featuresList.add(
                new Feature(
                    feature.getName(), hardwareFeatureService.getValuesByName(feature.getName())
                )
            );
        });
        int i = 0;

        for (HardwareEntity hardware : hardwareEntityList) {
            if (featureNameContains(featureNameList, hardware)) {
                featureNumList.add(
                    getInt(
                        featuresList.get(i),
                        hardwareFeatureService.getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
                    )
                );
                i++;
            }
            if (i == 1) {
                featureNumList.add(
                    getInt(
                        featuresList.get(i),
                        hardwareFeatureService.getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
                    )
                );
                i++;
            }
            if (i == featureNameList.size()) {
                break;
            }
        }

        String result = new ObjectMapper().writeValueAsString(featuresList);

        model.addAttribute("arr", featuresList);
        model.addAttribute("arr2", featureNumList);
        model.addAttribute("json", result);
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
        ArrayList<HardwareEntity> tempList = creatorService.create(
            price,
            goal,
            isSSD,
            new ArrayList<>(
                Arrays.asList(
                    range.core_amount, range.clock_rate, range.video_memory, range.volume_ram, range.volume_hdd, range.volume_ssd
                )
            ),
            featureNumList,
            hardwareEntityList,
            featureNameList,
            featuresList
        );
        hardwareEntityList.clear();
        hardwareEntityList = tempList;

        for (HardwareEntity hardwareEntity : hardwareEntityList) {
            temp += hardwareEntity.getPrice();
        }
        totalPrice = temp;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        featuresList.clear();
        featureNumList.clear();

        featureNameList.forEach(feature -> {
            featuresList.add(
                new Feature(
                    feature.getName(), hardwareFeatureService.getValuesByName(feature.getName())
                )
            );
        });
        int i = 0;

        for (HardwareEntity hardware : hardwareEntityList) {
            if (featureNameContains(featureNameList, hardware)) {
                featureNumList.add(
                    getInt(
                        featuresList.get(i),
                        hardwareFeatureService.getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
                    )
                );
                i++;
            }
            if (i == 1) {
                featureNumList.add(
                    getInt(
                        featuresList.get(i),
                        hardwareFeatureService.getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
                    )
                );
                i++;
            }
            if (i == featureNameList.size()) {
                break;
            }
        }

        String result = new ObjectMapper().writeValueAsString(featuresList);

        model.addAttribute("arr", featuresList);
        model.addAttribute("arr2", featureNumList);
        model.addAttribute("json", result);
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
        HardwareEntity[] arr = new HardwareEntity[hardwareEntityList.size()];
        for(int i = 0; i < hardwareEntityList.size(); ++i) {
            arr[i] = hardwareEntityList.get(i);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        assemblyService.save(arr, userService.loadUserByUsername(user.getUsername()), Double.parseDouble(df.format(totalPrice)));

        double temp = 0;
        for (HardwareEntity hardwareEntity : hardwareEntityList)
            temp += hardwareEntity.getPrice();
        totalPrice = temp;



        featuresList.clear();
        featureNumList.clear();

        featureNameList.forEach(feature -> {
            featuresList.add(
                new Feature(
                    feature.getName(), hardwareFeatureService.getValuesByName(feature.getName())
                )
            );
        });
        int i = 0;

        for (HardwareEntity hardware : hardwareEntityList) {
            if (featureNameContains(featureNameList, hardware)) {
                featureNumList.add(
                    getInt(
                        featuresList.get(i),
                        hardwareFeatureService.getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
                    )
                );
                i++;
            }
            if (i == 1) {
                featureNumList.add(
                    getInt(
                        featuresList.get(i),
                        hardwareFeatureService.getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
                    )
                );
                i++;
            }
            if (i == featureNameList.size()) {
                break;
            }
        }

        String result = new ObjectMapper().writeValueAsString(featuresList);

        model.addAttribute("arr", featuresList);
        model.addAttribute("arr2", featureNumList);
        model.addAttribute("json", result);
        model.addAttribute("price", price);
        model.addAttribute("goal", goal);
        model.addAttribute("isSSD", isSSD);
        model.addAttribute("hardwares", hardwareEntityList);
        model.addAttribute("totalPrice", df.format(totalPrice));
        model.addAttribute("active", user != null);
        return "pc";
    }
    public int getInt(Feature features, HardwareFeature feature) {
        int i = 0;
        for (String str : features.getFeatureValues()) {
            if (str.equals(feature.getValue())) {
                return i;
            }
            i++;
        }
        return 0;
    }
    public boolean featureNameContains(ArrayList<Hardware.Feature> featureNameList, HardwareEntity hardwareEntity) {
        for (Hardware.Feature feature : Hardware.valueOf(hardwareEntity.getType().getName().toUpperCase()).getFeature()) {
            if (featureNameList.contains(feature)) {
                return true;
            }
        }
        return false;
    }
}
