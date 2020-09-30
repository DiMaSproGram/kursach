package app.service.impl;

import app.entity.Assembly;
import app.entity.HardwareEntity;
import app.entity.User;
import app.repository.AssemblyRepo;
import app.service.AssemblyHardwareService;
import app.service.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssemblyServiceImpl implements AssemblyService {

    private final AssemblyRepo assemblyRepo;
    private final AssemblyHardwareService assemblyHardwareService;

    @Override
    public void save(HardwareEntity[] arr, User user, double totalPrice) {
        Assembly assembly = new Assembly( user, totalPrice);
        assemblyRepo.save(assembly);
        assemblyHardwareService.save(assembly, arr);
    }

    @Override
    public HashMap<String, ArrayList<HardwareEntity>> getByUser(int userId) {
        ArrayList<Assembly> allAssembly = (ArrayList<Assembly>) assemblyRepo.findAll();
        HashMap<String, ArrayList<HardwareEntity>> assemblyMap = new HashMap<>();
        int i = 0;
        for(Assembly assembly : allAssembly) {
            if (assembly.getUser().getId() == userId) {
                String goal = defineGoal(
                    (List<HardwareEntity>) assemblyHardwareService.getAssembly(assembly.getId())
                );
                assemblyMap.put(
                    goal + assembly.getTotalPrice() + "руб.@#collapse" + i,
                    (ArrayList<HardwareEntity>) assemblyHardwareService.getAssembly(assembly.getId())
                );
                i++;
            }
        }

        return assemblyMap;
    }

    private String defineGoal(List<HardwareEntity> list) {
        return list.get(0).getPrice() > list.get(1).getPrice()
            ? "Компьютер для работы за "
            : "Компьютер для гейминга за ";
    }
}
