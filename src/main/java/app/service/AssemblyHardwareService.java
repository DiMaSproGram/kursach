package app.service;

import app.entity.Assembly;
import app.entity.AssemblyHardware;
import app.entity.Hardware;

public interface AssemblyHardwareService {
    void save(Assembly assembly, Hardware[] hardware);
    Iterable<Hardware> getAssembly(int id);
}
