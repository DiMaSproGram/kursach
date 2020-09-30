package app.service.impl;

import app.common.service.AbstractService;
import app.entity.HardwareEntity;
import app.payload.Hardware;
import app.entity.HardwareType;
import app.repository.AssemblyHardwareRepo;
import app.repository.HardwareRepo;
import app.repository.HardwareTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class HardwareTypeService extends AbstractService<HardwareType, HardwareTypeRepo> {

    public HardwareTypeService(HardwareTypeRepo repository) {
        super(repository);
    }

    @PostConstruct
    public void createHardwareType() {
        if(new ArrayList<>((ArrayList<HardwareType>)this.getAll()).size() == 0) {
            for(Hardware hardware : Hardware.values()) {
                repository.save(new HardwareType(hardware.getName()));
            }
        }
    }

    public Iterable<HardwareType> getAll() {
        return repository.findAll();
    }

    public void addHardwareType(String name) {
        repository.save(new HardwareType(name));
    }

    public boolean isExist(int id) {
        return repository.findById(id).isPresent();
    }

    public HardwareType findByName(String name) {
        ArrayList<HardwareType> arrayList = (ArrayList<HardwareType>) repository.findAll();
        for(HardwareType hardwareType : arrayList) {
            if (hardwareType.getName().equals(name)) {
                return hardwareType;
            }
        }
        return null;
    }

    public HardwareType findById(int id) {
        return repository.findById(id).get();
    }
}
