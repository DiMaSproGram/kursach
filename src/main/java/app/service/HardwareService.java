package app.service;

import app.entity.Hardware;
import app.entity.HardwareType;

public interface HardwareService {
    Iterable<Hardware> getAll();
    Iterable<Hardware> getAllByType(String name);
    Iterable<Hardware> getAllByType(int id);
    void addHardware(Hardware hardware);
    void deleteAll();
    Hardware findById(int id);
}
