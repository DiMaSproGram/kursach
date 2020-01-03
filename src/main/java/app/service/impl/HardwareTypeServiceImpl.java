package app.service.impl;

import app.entity.Hardware;
import app.entity.HardwareType;
import app.repository.HardwareRepo;
import app.repository.HardwareTypeRepo;
import app.service.HardwareService;
import app.service.HardwareTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HardwareTypeServiceImpl implements HardwareTypeService {
    @Autowired
    public HardwareTypeRepo hardwareTypeRepo;

    @Override
    public Iterable<HardwareType> getAll() {
        return hardwareTypeRepo.findAll();
    }

    @Override
    public void addHardwareType(String name) {
        hardwareTypeRepo.save(new HardwareType(name));
    }

    @Override
    public boolean isExist(int id) {
        return hardwareTypeRepo.findById(id).isPresent();
    }

    @Override
    public HardwareType findByName(String name) {
        ArrayList<HardwareType> arrayList = (ArrayList<HardwareType>) hardwareTypeRepo.findAll();
        for(HardwareType hardwareType : arrayList)
            if(hardwareType.getName().equals(name))
                return hardwareType;
        return null;
    }

    @Override
    public HardwareType findById(int id) {
        return hardwareTypeRepo.findById(id).get();
    }
}
