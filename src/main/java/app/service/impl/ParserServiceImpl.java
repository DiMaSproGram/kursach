package app.service.impl;

import app.common.StringUtils;
import app.payload.Hardware;
import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import app.payload.HardwarePayload;
import app.payload.Page;
import app.payload.Products;
import app.service.HardwareService;
import app.service.HardwareTypeService;
import app.service.ParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
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
public class ParserServiceImpl implements ParserService {

    private final HardwareServiceImpl hardwareService;
    private final HardwareTypeService hardwareTypeService;
    private final HardwareFeatureService hardwareFeatureService;

    boolean isDefect = false;

//    @Scheduled(fixedRate = 604800000)
    @Override
    public void start() {
        try {
            System.out.println(LocalTime.now());

            hardwareFeatureService.deleteAll(hardwareService);
            hardwareService.deleteAll();

//            parsing(Hardware.HDD);
            for (Hardware hardware : Hardware.values()) {
                parsing(hardware);
                System.out.println(hardware.getName() + " are parsed");
            }

            System.out.println(LocalTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsing(Hardware hardware) throws IOException {
        int page = 1;
        String json = doRequest(hardware.getParseUrl() + page, "GET");
        Page lastPage = new ObjectMapper()
            .readerFor(Page.class)
            .readValue(json);

        while (page <= Integer.parseInt(lastPage.getLastPage())) {
            parsingPage(hardware, hardware.getParseUrl() + page);
            page++;
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

    private HardwareFeature parseHardwareFeature(Hardware.Feature feature, String hardwareUrl, HardwareEntity hardwareEntity) throws IOException {
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
        return new HardwareFeature(feature.getName(), StringUtils.trimParenthesesContent(value), hardwareService.findByName(hardwareEntity.getName()));
//        return new HardwareFeature(feature.getName(), value, hardwareService.findByName(hardwareEntity.getName()));
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

//    private void parsing(Hardware hardware) throws IOException {
//        Document doc;
//        Elements ul;
//        Elements li;
//        String parserUrl = hardware.getParseUrl();
//
//        while (true) {
//            if (parsePage(hardware, parserUrl) == 1) {
//                break;
//            }
//            doc = Jsoup.connect(parserUrl).get();
//            ul = doc.select("ul.pagination");
//            if (ul.toString().equals("")) {
//                break;
//            }
//            li = ul.select("li");
//            if(li.last().attr("class").equals("active")) {
//                break;
//            }
//            parserUrl = li.get(li.size() - 2).select("a").attr("href");
//            System.out.println(parserUrl);
//        }
//    }
//
//    private int parsePage(Hardware hardware, String url) throws IOException {
//        Document doc = Jsoup.connect(url).get();
//        List<Element> productList = doc.select("div.product-thumb");
//        Element p;
//        String title;
//        String imageLink;
//        String hardwareLink;
//        double price;
//
//        for(int i = 0; i < productList.size(); ++i) {
//            if (i % 5 != 0) {
//                continue;
//            }
//
//            p = productList.get(i).select("div.caption .price").first();
//            String priceTitle = p.attr("data-price");
//            if (p.text().equals("Товар отсутствует")) {
//                return 1;
//            }
//            price = Double.parseDouble(priceTitle);
//
//            title = productList.get(i).select("div.caption a").text();
//            title = trimRus(title);
//
//            imageLink = productList.get(i).select("div.image a img").first().attr("src");
//
//            hardwareLink = productList.get(i).select("div.caption a").attr("href");
//
//            if (hardware.getFeature() != Hardware.Feature.NONE) {
//                hardwareService.addHardware(
//                    new HardwareEntity(
//                        title,
//                        price,
//                        imageLink,
//                        hardwareLink,
//                        hardwareTypeService.findByName(hardware.getName()),
//                        parseHardware(hardware, hardwareLink)
//                    )
//                );
//            }
//            else {
//                hardwareService.addHardware(
//                    new HardwareEntity(
//                        title,
//                        price,
//                        imageLink,
//                        hardwareLink,
//                        hardwareTypeService.findByName(hardware.getName())
//                    )
//                );
//            }
//
//            System.out.println(title + " " + price + " " + imageLink);
//        }
//        return 0;
//    }
//
//    private HardwareFeature parseHardware(Hardware hardware, String url) throws IOException {
//        Document doc = Jsoup.connect(url).get();
//        String productDataRowName = "div.product-data.row";
//        int productDataRowNum = 1;
//        String colValueName = "div.col-xs-7.col-sm-8";
//        String colValue = "div.col-xs-5.col-sm-4";
//
//        if (hardware.getFeature() == Hardware.Feature.RECOMMEND_WATT) {
//            String phantomJsPath = PhantomJsDowloader.getPhantomJsPath();
//            productDataRowNum = 3;
//            colValueName = "div.after.col-xs-7.col-md-6";
//            colValue = "div.col-xs-5.col-md-6";
//        }
//
//        Element productThumb = doc.select(productDataRowName).get(productDataRowNum);
//        List<Element> characteristicList = productThumb.select(colValueName);
//        int characterNumber = 0;
//
//        for (Element element : characteristicList) {
//            if (
//                element.select("span").text()
//                    .equals(hardware.getFeature().getName())
//            ) {
//                break;
//            }
//            characterNumber++;
//        }
//
//        String value = productThumb.select(colValue).get(characterNumber).text();
//
//        return new HardwareFeature(hardware.getName(), value);
//    }
//

}
