package app.service.impl;

import app.common.service.AbstractService;
import app.entity.Assembly;
import app.entity.AssemblyHardware;
import app.entity.HardwareEntity;
import app.repository.AssemblyHardwareRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class AssemblyHardwareService extends AbstractService<AssemblyHardware, AssemblyHardwareRepo> {

  public AssemblyHardwareService(AssemblyHardwareRepo repository) {
    super(repository);
  }

  public void save(Assembly assembly, HardwareEntity[] hardwareEntity) {
    for (HardwareEntity hardware : hardwareEntity) {
      repository.save(new AssemblyHardware(assembly, hardware));
    }
  }

  public Iterable<HardwareEntity> getAssembly(int id) {
    ArrayList<AssemblyHardware> arrayList = (ArrayList<AssemblyHardware>) repository.findAll();
    ArrayList<HardwareEntity> hardwareEntityList = new ArrayList<>();

    for (AssemblyHardware assemblyHardware : arrayList) {
      if (assemblyHardware.getAssembly().getId() == id) {
        hardwareEntityList.add(assemblyHardware.getHardwareEntity());
      }
    }
    return hardwareEntityList;
  }

  public ArrayList<HardwareEntity> getAllHardware() {
    ArrayList<AssemblyHardware> list = (ArrayList<AssemblyHardware>) repository.findAll();
    ArrayList<HardwareEntity> result = new ArrayList<>();
    list.forEach(el -> result.add(el.getHardwareEntity()));
    return result;
  }

  public HashSet<HardwareEntity> getAllHardwareSet() {
    ArrayList<AssemblyHardware> list = (ArrayList<AssemblyHardware>) repository.findAll();
    HashSet<HardwareEntity> result = new HashSet<>();
    list.forEach(el -> result.add(el.getHardwareEntity()));
    return result;
  }

}
