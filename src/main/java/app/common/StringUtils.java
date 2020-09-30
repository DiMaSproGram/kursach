package app.common;

import app.entity.HardwareEntity;

import java.util.Comparator;
import java.util.List;

public class StringUtils {

  public static boolean haveAllStrings(String o, List<String> list) {
    boolean isHave = true;

    for(String elem : list) {
      if (!elem.equals(o)) {
        isHave = false;
        break;
      }
    }
    return isHave;
  }

  public static String trimRus(String s) {
    s = s.replaceAll("[а-яА-Я]","").trim();
    return s;
  }

  public static String trimParenthesesContent(String s) {
    s = s.replaceAll("\\s*\\([^\\)]*\\)\\s*", " ").trim();
    return s;
  }

  public static Comparator<String> comparatorStringNum = (hardware1, hardware2) -> {
    Double o1 = Double.parseDouble(hardware1.split(" ")[0]);
    Double o2 = Double.parseDouble(hardware2.split(" ")[0]);
    return o1.compareTo(o2);
  };

  public static Comparator<String> comparatorVolume = (hardware1, hardware2) -> {
    Double o1 = Double.parseDouble(hardware1.split(" ")[0]);
    if (hardware1.split(" ")[1].equals("ТБ") || hardware1.split(" ")[1].equals("TБ")) {
      o1 *= 1000;
    }

    Double o2 = Double.parseDouble(hardware2.split(" ")[0]);
    if (hardware2.split(" ")[1].equals("ТБ") || hardware2.split(" ")[1].equals("TБ")) {
      o2 *= 1000;
    }
    return o1.compareTo(o2);
  };
}
