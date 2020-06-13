package app.service.impl;

import app.entity.Assembly;
import app.entity.AssemblyHardware;
import app.entity.HardwareEntity;
import app.repository.AssemblyHardwareRepo;
import app.service.AssemblyHardwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AssemblyHardwareServiceImpl implements AssemblyHardwareService {

    private final AssemblyHardwareRepo assemblyHardwareRepo;

    @Override
    public void save(Assembly assembly, HardwareEntity[] hardwareEntity) {
        for (HardwareEntity hardware : hardwareEntity) {
            assemblyHardwareRepo.save(new AssemblyHardware(assembly, hardware));
        }
    }

    @Override
    public Iterable<HardwareEntity> getAssembly(int id) {
        ArrayList<AssemblyHardware> arrayList = (ArrayList<AssemblyHardware>) assemblyHardwareRepo.findAll();
        ArrayList<HardwareEntity> hardwareEntityList = new ArrayList<>();

        for(AssemblyHardware assemblyHardware : arrayList)
            if(assemblyHardware.getAssembly().getId() == id)
                hardwareEntityList.add(assemblyHardware.getHardwareEntity());

        return hardwareEntityList;
    }

    @Override
    public ArrayList<HardwareEntity> getAllHardware() {
        ArrayList<AssemblyHardware> list = (ArrayList<AssemblyHardware>) assemblyHardwareRepo.findAll();
        ArrayList<HardwareEntity> result = new ArrayList<>();
        list.forEach(el -> result.add(el.getHardwareEntity()));
        return result;
    }


}
