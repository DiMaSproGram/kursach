package app.service.impl;

import app.entity.Hardware;
import app.service.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ParserServiceImpl implements ParserService {
    public enum ParserURL {
        PROCESSOR_URL ("https://kst.by/kompyutery-i-komplektuyushchie/processory/price/194-4035/sklad/7?sort=p.price&order=ASC&limit=100", "processor"),
        VIDEO_CARD_URL ("https://kst.by/kompyutery-i-komplektuyushchie/videokarty/price/100-6000/sklad/7?sort=p.price&order=ASC&limit=100", "video_card"),
        MOTHERBOARD_URL ("https://kst.by/kompyutery-i-komplektuyushchie/materinskie-platy/price/55-650/sklad/7?sort=p.price&order=ASC&limit=100", "motherboard"),
        RAM_URL ("https://kst.by/kompyutery-i-komplektuyushchie/operativnaya-pamyat/price/40-800/sklad/7?sort=p.price&order=ASC&limit=100", "ram"),
        HDD_URL ("https://kst.by/kompyutery-i-komplektuyushchie/jestkie-diski/price/31-674/sklad/7?sort=p.price&order=ASC&limit=100", "hdd"),
        SSD_URL ("https://kst.by/kompyutery-i-komplektuyushchie/ssd/price/21-420/sklad/7?sort=p.price&order=ASC&limit=100", "ssd"),
        POWER_SUPPLY_URL ("https://kst.by/kompyutery-i-komplektuyushchie/bloki-pitaniya/price/21-160/sklad/7?sort=p.price&order=ASC&limit=100", "power_supply"),
        COOLERS_URL ("https://kst.by/kompyutery-i-komplektuyushchie/sistemy-ohlajdeniya/price/10-315/sklad/7/termokontrol/net?sort=p.price&order=ASC&limit=100", "coolers"),
        COMPUTER_CASE_URL ("https://kst.by/kompyutery-i-komplektuyushchie/korpusa/sklad/7?sort=p.price&order=ASC&limit=100", "computer_case");

        private String url;
        private String name;

        ParserURL(String url, String name){
            this.name = name;
            this.url = url;
        }
        public String getUrl() {
            return url;
        }
        public String getName() {
            return name;
        }
    }
    @Autowired
    private HardwareService hardwareService;
    @Autowired
    private HardwareTypeService hardwareTypeService;

//    @Scheduled(fixedRate = 604800000)
    @Override
    public void start() {
        try {
            hardwareService.deleteAll();
            if(!hardwareTypeService.isExist(1))
                for (ParserURL parserURL : ParserURL.values())
                    hardwareTypeService.addHardwareType(parserURL.getName());
            for (ParserURL parserURL : ParserURL.values())
                parsing(parserURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsing(ParserURL parserURL) throws IOException {
        Document doc;
        Elements ul;
        Elements li;
        String url = parserURL.getUrl();

        while (true) {
            if (parsPage(url, parserURL) == 1)
                break;
            doc = Jsoup.connect(url).get();
            ul = doc.select("ul.pagination");
            if (ul.toString().equals(""))
                break;
            li = ul.select("li");
            if(li.last().attr("class").equals("active"))
                break;
            url = li.get(li.size() - 2).select("a").attr("href");
            System.out.println(url);
        }
    }

    private int parsPage(String url, ParserURL parserURL) throws IOException {
        Document doc = Jsoup.connect(url).get();
        List<Element> productList = doc.select("div.product-thumb");
        Element p;
        Element image;
        String title;
        String imageLink;
        double price;

        for(int i = 0; i < productList.size(); ++i) {
            if(i % 5 != 0)
                continue;
            title = productList.get(i).select("div.caption a").text();
            title = trimRus(title);

            image = productList.get(i).select("div.image a img").first();
            imageLink = image.attr("src");

            p = productList.get(i).select("div.caption .price").first();
            String priceTitle = p.attr("data-price");
            if (p.text().equals("Товар отсутствует"))
                return 1;
            else
                price = Double.parseDouble(priceTitle);

            hardwareService.addHardware(new Hardware(title, price, imageLink, hardwareTypeService.findByName(parserURL.getName())));

            System.out.println(title + " " + price + " " + imageLink);
        }
        return 0;
    }

    private String trimRus(String s) {
        s = s.replaceAll("[а-яА-Я]","").trim();
        return s;
    }
}
