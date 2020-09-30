package app.service;

import app.common.Expression;
import app.entity.HardwareEntity;
import app.payload.Hardware;
import app.payload.Pagination;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public interface HardwareService {
  Iterable<HardwareEntity> getAll();

  List<HardwareEntity> getAllByType(String type);

  List<HardwareEntity> getAllByType(int id);

  List<HardwareEntity> getAllBySearching(String search, String type);

  List<HardwareEntity> getAllBySearching(ArrayList<HardwareEntity> inputList, String search, String type, Model model);

  List<HardwareEntity> getAllByPrice(double minPrice, double maxPrice, String type);

  List<HardwareEntity> getAllByPrice(double price, Expression func, String type);

  List<HardwareEntity> getAllByFeature(Hardware.Feature feature, String featureVal);

  List<HardwareEntity> getAllByFeature(String feature, String type);

  List<HardwareEntity> filterByPrice(double maxPrice, double minPrice, String type);

  List<HardwareEntity> filterByPrice(ArrayList<HardwareEntity> inputList, double maxPrice, double minPrice, String type);

  List<HardwareEntity> filterByFeatures(List<String> features, String type);

  List<HardwareEntity> filterByFeatures(ArrayList<HardwareEntity> inputList, List<String> features, String type);

  List<HardwareEntity> filterByAll(double maxPrice, double minPrice, List<String> features, String type);

  List<HardwareEntity> filterByAll(ArrayList<HardwareEntity> inputList, double maxPrice, double minPrice, List<String> features, String type);

  void addHardware(HardwareEntity hardwareEntity);

  void deleteAll();

  HardwareEntity findById(int id);

  HardwareEntity findByName(String name);

  void fillPaginationList(String type, ArrayList<ArrayList<HardwareEntity>> paginationList, ArrayList<Pagination> pagesMap);
}
