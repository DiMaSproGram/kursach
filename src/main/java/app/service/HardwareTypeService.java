package app.service;

import app.entity.HardwareType;

public interface HardwareTypeService {
  void addHardwareType(String name);
  boolean isExist(int id);
  Iterable<HardwareType> getAll();
  HardwareType findByName(String name);
  HardwareType findById(int id);
}
