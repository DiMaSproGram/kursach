package app.payload;

import app.common.StringUtils;
import app.entity.HardwareEntity;
import org.apache.logging.log4j.util.PropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Hardware {
  PROCESSOR (
      "https://catalog.onliner.by/sdapi/catalog.api/search/cpu?price[from]=100.00&price[to]=4500.00&in_stock=1&order=price:asc&order=price:asc&page=",
      "processor",
      new ArrayList<>(Arrays.asList(Feature.SOCKET, Feature.BOX, Feature.CORE_AMOUNT, Feature.CLOCK_RATE))
  ),
  VIDEO_CARD (
      " https://catalog.onliner.by/sdapi/catalog.api/search/videocard?price[from]=100.00&price[to]=6000.00&in_stock=1&order=price:asc&page=",
      "video_card",
      new ArrayList<>(Arrays.asList(Feature.RECOMMEND_WATT, Feature.VIDEO_MEMORY))
  ),
  MOTHERBOARD (
      "https://catalog.onliner.by/sdapi/catalog.api/search/motherboard?price[from]=55.00&price[to]=1000.00&in_stock=1&order=price:asc&page=",
      "motherboard",
      new ArrayList<>(Arrays.asList(Feature.SOCKET))
  ),
  RAM (
      "https://catalog.onliner.by/sdapi/catalog.api/search/dram?price[from]=40.00&price[to]=1000.00&in_stock=1&order=price:asc&page=",
      "ram",
      new ArrayList<>(Arrays.asList(Feature.VOLUME_RAM, Feature.TYPE))
  ),
  HDD (
      "https://catalog.onliner.by/sdapi/catalog.api/search/hdd?price[from]=30.00&price[to]=1000.00&in_stock=1&order=price:asc&page=",
      "hdd",
      new ArrayList<>(Arrays.asList(Feature.SPINDLE_SPEED, Feature.VOLUME_HDD))
  ),
  SSD (
      "https://catalog.onliner.by/sdapi/catalog.api/search/ssd?price[from]=50.00&price[to]=1000.00&in_stock=1&group=0&order=price:asc&page=",
      "ssd",
      new ArrayList<>(Arrays.asList(Feature.VOLUME_SSD))
  ),
  POWER_SUPPLY (
      "https://catalog.onliner.by/sdapi/catalog.api/search/powersupply?price[from]=20.00&price[to]=400.00&in_stock=1&power_purpose[0]=pc&power_purpose[1]=game&power_purpose[operation]=union&order=price:asc&page=",
      "power_supply",
      new ArrayList<>(Arrays.asList(Feature.WATT))
  ),
  COOLERS (
      "https://catalog.onliner.by/sdapi/catalog.api/search/fan?price[from]=10.00&price[to]=400.00&in_stock=1&type_fan[0]=cpu&type_fan[operation]=union&order=price:asc&group=0&order=price:asc&page=",
      "coolers",
      new ArrayList<>(Arrays.asList(Feature.SOCKET, Feature.DISSIPATION_POWER))
  ),
  COMPUTER_CASE (
      "https://catalog.onliner.by/sdapi/catalog.api/search/chassis?in_stock=1&case_class[0]=miditower&case_class[operation]=union&formfactor[0]=atx&formfactor[operation]=union&group=0&order=price:asc&page=",
      "computer_case",
      new ArrayList<>(Arrays.asList(Feature.NONE))
  );

  private String parseUrl;
  private String name;
  private ArrayList<Feature> feature;

  Hardware(String parseUrl, String name, ArrayList<Feature> feature) {
    this.parseUrl = parseUrl;
    this.name = name;
    this.feature = feature;
  }

  public String getParseUrl() {
    return parseUrl;
  }

  public List<Feature> getFeature() {
    return feature;
  }

  public String getName() {
    return name;
  }

  public enum Feature {
    NONE("", "", String::compareTo),
    SOCKET("socket","Сокет",  String::compareTo),
    CORE_AMOUNT("core_amount","Количество ядер", StringUtils.comparatorStringNum),
    CLOCK_RATE("clock_rate","Базовая тактовая частота", StringUtils.comparatorStringNum),
    VIDEO_MEMORY("video_memory","Видеопамять", StringUtils.comparatorStringNum),
    RECOMMEND_WATT("recommend_watt","Рекомендуемый блок питания", StringUtils.comparatorStringNum),
    VOLUME_RAM("volume_ram","Объем", StringUtils.comparatorVolume),
    VOLUME_HDD("volume_hdd","Объём", StringUtils.comparatorVolume),
    VOLUME_SSD("volume_ssd","Объём", StringUtils.comparatorVolume),
    SPINDLE_SPEED("spindle_speed","Скорость вращения шпинделя", StringUtils.comparatorStringNum),
    TYPE("type","Тип", String::compareTo),
    WATT("watt", "Мощность", StringUtils.comparatorStringNum),
    DISSIPATION_POWER("dissipation_power","Рассеиваемая мощность", StringUtils.comparatorStringNum),
    BOX("box", "Боксовый процессор", String::compareTo);

    private String name;
    private String parsingName;
    private Comparator<String> comparator;

    Feature(String name, String parsingName, Comparator<String>  comparator) {
      this.name = name;
      this.parsingName = parsingName;
      this.comparator = comparator;
    }
    public String getName() {
      return name;
    }
    public String getParsingName() {
      return parsingName;
    }
    public Comparator<String> getComporator() {
      return comparator;
    }
  }

}