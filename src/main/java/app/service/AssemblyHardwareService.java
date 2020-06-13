package app.service;

import app.entity.Assembly;
import app.entity.HardwareEntity;

import java.util.ArrayList;

public interface AssemblyHardwareService {
    void save(Assembly assembly, HardwareEntity[] hardwareEntity);
    Iterable<HardwareEntity> getAssembly(int id);
    ArrayList<HardwareEntity> getAllHardware();
}
