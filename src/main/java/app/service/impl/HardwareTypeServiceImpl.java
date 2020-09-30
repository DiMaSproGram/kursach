package app.service.impl;

import app.payload.Hardware;
import app.entity.HardwareType;
import app.repository.HardwareTypeRepo;
import app.service.HardwareTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class HardwareTypeServiceImpl implements HardwareTypeService {

    public final HardwareTypeRepo hardwareTypeRepo;

    @PostConstruct
    public void createHardwareType() {
        if(new ArrayList<>((ArrayList<HardwareType>)this.getAll()).size() == 0) {
            for(Hardware hardware : Hardware.values()) {
                hardwareTypeRepo.save(new HardwareType(hardware.getName()));
            }
        }
    }

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
        for(HardwareType hardwareType : arrayList) {
            if (hardwareType.getName().equals(name)) {
                return hardwareType;
            }
        }
        return null;
    }

    @Override
    public HardwareType findById(int id) {
        return hardwareTypeRepo.findById(id).get();
    }
}
