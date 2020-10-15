package app.service;

import app.common.service.AbstractService;
import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import app.payload.Feature;
import app.payload.Hardware;
import app.repository.HardwareFeatureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HardwareFeatureService extends AbstractService<HardwareFeature, HardwareFeatureRepo> {

  private final AssemblyHardwareService assemblyHardwareService;

  @Autowired
  public HardwareFeatureService(HardwareFeatureRepo repository, AssemblyHardwareService assemblyHardwareService) {
    super(repository);
    this.assemblyHardwareService = assemblyHardwareService;
  }

  public HardwareFeature getByHardwareIdAndName(int id, String name) {
    return repository.findByHardwareEntityIdAndName(id, name);
  }

  public ArrayList<HardwareFeature> getByHardwareEntityId(int id) {
    ArrayList<HardwareFeature> list = (ArrayList<HardwareFeature>) repository.findAll();
    list.removeIf(el -> el.getHardwareEntity().getId() != id);
    return list;
  }

  public HashMap<String, Feature> getAllFeature(String type) {
    HashMap<String, Feature> featuresMap = new HashMap<>();

    Hardware.valueOf(type.toUpperCase()).getFeature().forEach(
        feature -> featuresMap.put(
            Hardware.Feature.valueOf(feature.getName().toUpperCase()).getParsingName(), new Feature(
                feature.getName(), getValuesByName(feature.getName())
            )
        )
    );
    return featuresMap;
  }

  public List<HardwareFeature> getAllByNameAndHardwareType(
      String name,
      String type,
      HardwareService hardwareService
  ) {
    List<HardwareEntity> hardwareList = hardwareService.getAllByType(type);
    List<HardwareFeature> featureList = (List<HardwareFeature>) getAllByName(name);
    featureList.removeIf(feature -> !hardwareList.contains(feature.getHardwareEntity()));
    return featureList;
  }

  public Iterable<HardwareFeature> getAllByName(String name) {
    return repository.findAllByName(name);
  }

  public Iterable<HardwareFeature> getAllByNameAndValue(String name, String val) {
    return repository.findAllByNameAndValue(name, val);
  }

  public void fillFeatureNameListAndFeaturesList(
      ArrayList<Hardware.Feature> featureNameList,
      ArrayList<Feature> featuresList
  ) {
    featureNameList.forEach(feature -> {
      featuresList.add(
          new Feature(
              feature.getName(),
              getValuesByName(feature.getName())
          )
      );
    });
  }

  public void fillFeatureNumList(
      ArrayList<HardwareEntity> hardwareEntityList,
      ArrayList<Hardware.Feature> featureNameList,
      ArrayList<Integer> featureNumList,
      ArrayList<Feature> featuresList
  ) {
    int i = 0;
    for (HardwareEntity hardware : hardwareEntityList) {
      if (featureNameContains(featureNameList, hardware)) {
        featureNumList.add(
            getInt(
                featuresList.get(i),
                getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
            )
        );
        i++;
      }
      if (i == 1) {
        featureNumList.add(
            getInt(
                featuresList.get(i),
                getByHardwareIdAndName(hardware.getId(), featuresList.get(i).getFeatureName())
            )
        );
        i++;
      }
      if (i == featureNameList.size()) {
        break;
      }
    }
  }

  public TreeSet<String> getValuesByName(String name) {
    TreeSet<String> valueSet = new TreeSet<>(Hardware.Feature.valueOf(name.toUpperCase()).getComporator());
    List<HardwareFeature> list = (List<HardwareFeature>) repository.findAllByName(name);

    for (HardwareFeature feature : list) {
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
      repository.save(hardwareFeature);
    }
  }

  public void deleteAll(HardwareService hardwareService) {
    ArrayList<HardwareEntity> hardwareList = (ArrayList<HardwareEntity>) hardwareService.findAll();
    HashSet<HardwareEntity> hardwareListFromAssemble = assemblyHardwareService.getAllHardwareSet();

    for (HardwareEntity hardware : hardwareList) {
//      if (!hardwareListFromAssemble.contains(hardware)) {
      if (!myContains(hardwareListFromAssemble, hardware)) {
        repository.deleteAll(
            getByHardwareEntityId(
                hardware.getId()
            )
        );
      }
    }
  }

  public boolean myContains(HashSet<HardwareEntity> hardwareListFromAssemble, HardwareEntity hardware) {
    for (HardwareEntity element : hardwareListFromAssemble) {
      if (element.equals(hardware)){
        return true;
      }
    }
    return false;
  }

  public int getInt(Feature features, HardwareFeature feature) {
    int i = 0;
    for (String str : features.getFeatureValues()) {
      if (str.equals(feature.getValue())) {
        return i;
      }
      i++;
    }
    return 0;
  }

  public boolean featureNameContains(ArrayList<Hardware.Feature> featureNameList, HardwareEntity hardwareEntity) {
    for (Hardware.Feature feature : Hardware.valueOf(hardwareEntity.getType().getName().toUpperCase()).getFeature()) {
      if (featureNameList.contains(feature)) {
        return true;
      }
    }
    return false;
  }

}
