package app.service;

import app.common.StringUtils;
import app.common.ThreadExec;
import app.payload.Hardware;
import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import app.payload.HardwarePayload;
import app.payload.Page;
import app.payload.Products;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ParserService {

    private final HardwareService hardwareService;
    private final HardwareTypeService hardwareTypeService;
    private final HardwareFeatureService hardwareFeatureService;

    boolean isDefect = false;

//    @Scheduled(fixedRate = 604800000)
    public void start() {
        System.out.println(LocalTime.now());

        hardwareFeatureService.deleteAll(hardwareService);
        hardwareService.deleteAll();

        for (Hardware hardware : Hardware.values()) {
            new ThreadExec(this::parsing, hardware).run();
            System.out.println(hardware.getName() + " are parsed");
        }

        System.out.println(LocalTime.now());
    }

    private void parsing(Hardware hardware) throws IOException {
        int page = 1;
        String json = doRequest(hardware.getParseUrl() + page, "GET");
        Page lastPage = new ObjectMapper()
            .readerFor(Page.class)
            .readValue(json);

        for (; page <= Integer.parseInt(lastPage.getLastPage()); page++) {
            parsingPage(hardware, hardware.getParseUrl() + page);
        }
    }

    private void parsingPage(Hardware hardware, String pageUrl) throws IOException {
        String json = doRequest(pageUrl, "GET");

        Products products = new ObjectMapper()
            .readerFor(Products.class)
            .readValue(json);

        for (HardwarePayload hardwarePayload : products.getProducts()) {
            ArrayList<HardwareFeature> featureList = new ArrayList<>();
            HardwareEntity hardwareEntity = new  HardwareEntity(
                hardwarePayload.getName(),
                hardwarePayload.getDescription(),
                hardwarePayload.getPrice(),
                hardwarePayload.getImage(),
                hardwarePayload.getLink(),
                hardwareTypeService.findByName(hardware.getName())
            );

            if (!hardware.getFeature().contains(Hardware.Feature.NONE)) {
                for (Hardware.Feature feature : hardware.getFeature()) {
                    featureList.add(parseHardwareFeature(feature, hardwarePayload.getLink(), hardwareEntity));
                }
            }

            if (!isDefect){
                hardwareService.addHardware(hardwareEntity);
                for (HardwareFeature feature : featureList) {
                    feature.setHardwareEntity(hardwareService.findByName(hardwareEntity.getName()));
                    hardwareFeatureService.addHardwareFeature(feature);
                }
            }
            isDefect = false;
        }
    }

    private HardwareFeature parseHardwareFeature(
        Hardware.Feature feature,
        String hardwareUrl,
        HardwareEntity hardwareEntity
    ) throws IOException {
        if (feature == Hardware.Feature.BOX) {
            return new HardwareFeature(
                feature.getName(),
                hardwareEntity.getName().endsWith("(BOX)")
                ? "true"
                : "false",
                hardwareService.findByName(hardwareEntity.getName())
            );
        }
        Document doc = Jsoup.parse(doRequest(hardwareUrl, "GET"));
        Element table = doc.select("table.product-specs__table").first();
        Elements tbody = table.select("tbody tr");
        String value = "";

        for (Element element : tbody) {
            if (hardwareEntity.getType().getName().equals("ram")) {
                if (
                    Arrays.asList(element.select("td").text().split(" "))
                        .contains(feature.getParsingName())
                ) {
                    value = element.select("td").last().text();
                }
            }

            if (
                element.select("td").first()
                    .select("p.product-tip__term").text()
                    .equals(feature.getParsingName())
            ) {
                value = element.select("td").last().text();
            }
        }
        if (value.equals("")) {
            isDefect = true;
        }
        return new HardwareFeature(
            feature.getName(),
            StringUtils.trimParenthesesContent(value),
            hardwareService.findByName(hardwareEntity.getName())
        );
    }

    private String doRequest(String requestUrl, String requestType) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(requestType);
        con.setDoOutput(true);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
