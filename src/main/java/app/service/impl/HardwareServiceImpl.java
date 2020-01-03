package app.service.impl;

import app.entity.Hardware;
import app.entity.HardwareType;
import app.repository.HardwareRepo;
import app.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HardwareServiceImpl implements HardwareService {
    @Autowired
    public HardwareRepo hardware;

    @Override
    public Iterable<Hardware> getAll() {
        return hardware.findAll();
    }

    @Override
    public Iterable<Hardware> getAllByType(String name) {
        ArrayList<Hardware> resList = new ArrayList<>();
        ArrayList<Hardware> arrayList = (ArrayList<Hardware>) hardware.findAll();
        for (Hardware hardware : arrayList)
            if(hardware.getType().getName().equals(name))
                resList.add(hardware);

        return resList;
    }
    @Override
    public Iterable<Hardware> getAllByType(int id) {
        ArrayList<Hardware> resList = new ArrayList<>();
        ArrayList<Hardware> arrayList = (ArrayList<Hardware>) hardware.findAll();
        for (Hardware hardware : arrayList)
            if(hardware.getType().getId() == id)
                resList.add(hardware);

        return resList;
    }

    @Override
    public void addHardware(Hardware hardware) {
        this.hardware.save(hardware);
    }

    @Override
    public void deleteAll() {
        hardware.deleteAll();
    }

    @Override
    public Hardware findById(int id) {
        return hardware.findById(id).get();
    }
}
