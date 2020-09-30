package app.controller;

import app.common.StringUtils;
import app.entity.HardwareEntity;
import app.entity.User;
import app.payload.Feature;
import app.payload.Hardware;
import app.payload.Pagination;
import app.service.*;
import app.service.impl.HardwareFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final HardwareService hardwareService;
    private final HardwareFeatureService hardwareFeatureService;

    private String type;
    private HashMap<String, Feature> featuresMap = new HashMap<>();
    private ArrayList<ArrayList<HardwareEntity>> paginationList = new ArrayList<>();
    private ArrayList<Pagination> pagesMap = new ArrayList<>();
    private int page;

    @GetMapping
    public String catalog(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("active", user != null);
        return "catalog";
    }

    @GetMapping("/{path}")
    public String hardwares(@PathVariable("path") String path, @AuthenticationPrincipal User user, Model model) {
        type = path;
        page = 1;

        if (!featuresMap.isEmpty()) {
            featuresMap = new HashMap<>();
        }

        if (
            !Hardware.valueOf(type.toUpperCase())
                .getFeature()
                .contains(Hardware.Feature.NONE)
        ) {
            featuresMap = hardwareFeatureService.getAllFeature(type);
        }

        hardwareService.fillPaginationList(type, paginationList, pagesMap);

        model.addAttribute("filters", featuresMap);
        model.addAttribute("hardwares", paginationList.get(0));
        model.addAttribute("type", type);
        model.addAttribute("pages", pagesMap);
        model.addAttribute("currentPage", 1);
        model.addAttribute("nextPage", 2);
        model.addAttribute("previousPage", 1);
        model.addAttribute("active", user != null);
        return "hardware";
    }

    @GetMapping("/{path}/{page}")
    public String hardwares(
        @PathVariable("path") String path,
        @PathVariable("page") String page,
        @AuthenticationPrincipal User user,
        Model model
    ) {
        type = path;
        this.page = Integer.parseInt(page);

        if (!featuresMap.isEmpty()) {
            featuresMap = new HashMap<>();
        }

        if (
            !Hardware.valueOf(type.toUpperCase())
                .getFeature()
                .contains(Hardware.Feature.NONE)
        ) {
            featuresMap = hardwareFeatureService.getAllFeature(type);
        }

        hardwareService.fillPaginationList(type, paginationList, pagesMap);

        model.addAttribute("filters", featuresMap);
        model.addAttribute("hardwares", paginationList.get(this.page - 1));
        model.addAttribute("type", type);
        model.addAttribute("pages", pagesMap);
        model.addAttribute("currentPage", this.page);
        model.addAttribute(
            "nextPage",
            this.page == pagesMap.size()
                ? pagesMap.size() - 1
                : this.page + 1
        );
        model.addAttribute(
            "previousPage",
            this.page == 1
                ? 1
                : this.page - 1
        );
        model.addAttribute("active", user != null);

        return "hardware";
    }

    @PostMapping("/{path}/{page}/search")
    public String search(
        @PathVariable("path") String path,
        @PathVariable("page") String page,
        @RequestParam String search,
        @AuthenticationPrincipal User user,
        Model model
    ) throws InterruptedException {
        List<HardwareEntity> hardwares = hardwareService.getAllBySearching(
            paginationList.get(this.page - 1),
            search,
            type,
            model
        );

        model.addAttribute(
            "error",
            hardwares.isEmpty()
                ? "Подходящих комплектующих не найдено"
                : ""
        );
        model.addAttribute("filters", featuresMap);
        model.addAttribute("hardwares", hardwares);
        model.addAttribute("type", type);
        model.addAttribute("pages", pagesMap);
        model.addAttribute("currentPage", this.page);
        model.addAttribute(
            "nextPage",
            this.page == pagesMap.size()
                ? pagesMap.size() - 1
                : this.page + 1
        );
        model.addAttribute(
            "previousPage",
            this.page == 1
                ? 1
                : this.page - 1
        );
        model.addAttribute("active", user != null);
        Thread.sleep(1000);
        return "hardware";
    }

    @PostMapping("/{path}/{page}/filter")
    public String filter(
        @PathVariable("path") String path,
        @PathVariable("page") String page,
        @RequestParam String minPriceS,
        @RequestParam String maxPriceS,
        @AuthenticationPrincipal User user,
        Model model,
        @RequestParam String ...features
    ) throws InterruptedException {
        List<HardwareEntity> hardwares;

        if (
            minPriceS.isEmpty()
            && maxPriceS.isEmpty()
            && StringUtils.haveAllStrings("", Arrays.asList(features))
        ) {
            hardwares = paginationList.get(this.page - 1);
        } else {
            double minPrice = minPriceS.isEmpty() ? 0 : Double.parseDouble(minPriceS);
            double maxPrice = maxPriceS.isEmpty() ? 0 : Double.parseDouble(maxPriceS);

            hardwares = hardwareService.filterByAll(
                paginationList.get(this.page - 1),
                minPrice,
                maxPrice,
                Arrays.asList(features),
                type
            );

            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);
        }

        model.addAttribute(
            "error",
            hardwares.isEmpty()
                ? "Подходящих комплектующих не найдено"
                : ""
        );
        model.addAttribute("filters", featuresMap);
        model.addAttribute("hardwares", hardwares);
        model.addAttribute("pages", pagesMap);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", this.page);
        model.addAttribute(
            "nextPage",
            this.page == pagesMap.size()
                ? pagesMap.size() - 1
                : this.page + 1
        );
        model.addAttribute(
            "previousPage",
            this.page == 1
                ? 1
                : this.page - 1
        );
        model.addAttribute("active", user != null);
        Thread.sleep(1000);
        return "hardware";
    }


}
