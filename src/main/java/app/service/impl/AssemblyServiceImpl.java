package app.service.impl;

import app.entity.Assembly;
import app.entity.Hardware;
import app.entity.User;
import app.repository.AssemblyRepo;
import app.service.AssemblyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssemblyServiceImpl implements AssemblyService {
    @Autowired
    AssemblyRepo assemblyRepo;
    @Override
    public void save(Hardware[] arr, User user, double totalPrice) {
        assemblyRepo.save(new Assembly(arr, user, totalPrice));
    }

    @Override
    public Iterable<Assembly> getByUser(int userId) {
        ArrayList<Assembly> allAssembly = (ArrayList<Assembly>) assemblyRepo.findAll();
        ArrayList<Assembly> arrayList = new ArrayList<>();

        for(Assembly a : allAssembly)
            if(a.getUser().getId() == userId)
                arrayList.add(a);

        return arrayList;
    }
}
