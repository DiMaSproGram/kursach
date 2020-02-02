package app.service.impl;

import app.entity.Assembly;
import app.entity.Hardware;
import app.entity.User;
import app.repository.AssemblyRepo;
import app.service.AssemblyHardwareService;
import app.service.AssemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssemblyServiceImpl implements AssemblyService {
    @Autowired
    AssemblyRepo assemblyRepo;
    @Autowired
    AssemblyHardwareService assemblyHardwareService;

    @Override
    public void save(Hardware[] arr, User user, double totalPrice) {
        Assembly assembly = new Assembly( user, totalPrice);
        assemblyRepo.save(assembly);
        assemblyHardwareService.save(assembly, arr);
    }

    @Override
    public ArrayList<ArrayList<Hardware>> getByUser(int userId) {
        ArrayList<Assembly> allAssembly = (ArrayList<Assembly>) assemblyRepo.findAll();
        ArrayList<ArrayList<Hardware>> arrayList = new ArrayList<>();

        for(Assembly a : allAssembly)
            if(a.getUser().getId() == userId)
                arrayList.add((ArrayList<Hardware>) assemblyHardwareService.getAssembly(a.getId()));

        return arrayList;
    }
}
