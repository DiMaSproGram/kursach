package app.service.impl;

import app.entity.Assembly;
import app.entity.AssemblyHardware;
import app.entity.Hardware;
import app.repository.AssemblyHardwareRepo;
import app.service.AssemblyHardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssemblyHardwareServiceImpl implements AssemblyHardwareService {
    @Autowired
    public AssemblyHardwareRepo assemblyHardwareRepo;

    @Override
    public void save(Assembly assembly, Hardware[] hardware) {
        for (Hardware hardware1 : hardware)
            assemblyHardwareRepo.save(new AssemblyHardware(assembly, hardware1));
    }

    @Override
    public Iterable<Hardware> getAssembly(int id) {
        ArrayList<AssemblyHardware> arrayList = (ArrayList<AssemblyHardware>) assemblyHardwareRepo.findAll();
        ArrayList<Hardware> hardwareList = new ArrayList<>();

        for(AssemblyHardware assemblyHardware : arrayList)
            if(assemblyHardware.getAssembly().getId() == id)
                hardwareList.add(assemblyHardware.getHardware());

        return hardwareList;
    }
}
