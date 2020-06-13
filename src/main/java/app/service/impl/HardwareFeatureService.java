package app.service.impl;

import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import app.payload.Hardware;
import app.repository.HardwareFeatureRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HardwareFeatureService {

  private final HardwareFeatureRepo hardwareFeatureRepo;
  private final AssemblyHardwareServiceImpl assemblyHardwareService;

  public HardwareFeature getByHardwareIdAndName(int id, String name) {
    return hardwareFeatureRepo.findByHardwareEntityIdAndName(id, name);
  }

  public ArrayList<HardwareFeature> getByHardwareEntityId(int id) {
    ArrayList<HardwareFeature> list = (ArrayList<HardwareFeature>) hardwareFeatureRepo.findAll();
    list.removeIf(el -> el.getHardwareEntity().getId() != id);
    return list;
  }

  public List<HardwareFeature> getAllByNameAndHardwareType( String name, String type, HardwareServiceImpl hardwareService) {
    List<HardwareEntity> hardwareList = hardwareService.getAllByType(type);
    List<HardwareFeature> featureList = (List<HardwareFeature>) getAllByName(name);
    featureList.removeIf(feature -> !hardwareList.contains(feature.getHardwareEntity()));
    return featureList;
  }

  public Iterable<HardwareFeature> getAllByName(String name) {
     return hardwareFeatureRepo.findAllByName(name);
  }

  public Iterable<HardwareFeature> getAllByNameAndValue(String name, String val) {
    return hardwareFeatureRepo.findAllByNameAndValue(name, val);
  }

  public TreeSet<String> getValuesByName(String name) {
    TreeSet<String> valueSet = new TreeSet<>(Hardware.Feature.valueOf(name.toUpperCase()).getComporator());
    List<HardwareFeature> list = (List<HardwareFeature>) hardwareFeatureRepo.findAllByName(name);

    for(HardwareFeature feature : list) {
      if (feature.getValue().split(", ").length > 0) {
        valueSet.addAll(Arrays.asList(feature.getValue().split(", ")));
      } else {
        valueSet.add(feature.getValue());
      }
    }
    return valueSet;
  }

  public void addHardwareFeature(HardwareFeature hardwareFeature) {
    ArrayList<HardwareEntity> hardwareListFromAssemble = assemblyHardwareService.getAllHardware();
    if (!hardwareListFromAssemble.contains(hardwareFeature.getHardwareEntity())) {
      hardwareFeatureRepo.save(hardwareFeature);
    }
  }

  public void deleteById(int id) {
    hardwareFeatureRepo.deleteById(id);
  }

  public void deleteAll(HardwareServiceImpl hardwareService) {
    ArrayList<HardwareEntity> hardwareList = (ArrayList<HardwareEntity>) hardwareService.getAll();
    ArrayList<HardwareEntity> hardwareListFromAssemble = assemblyHardwareService.getAllHardware();

    for (HardwareEntity hardware : hardwareList) {
      if (!hardwareListFromAssemble.contains(hardware)) {

        hardwareFeatureRepo.deleteAll(getByHardwareEntityId(hardwareService.findByName(hardware.getName()).getId()));
      }
    }
  }

}
