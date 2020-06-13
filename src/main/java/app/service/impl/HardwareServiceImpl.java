package app.service.impl;

import app.common.Expression;
import app.common.StringUtils;
import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import app.payload.Hardware;
import app.repository.HardwareRepo;
import app.service.HardwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HardwareServiceImpl implements HardwareService {

    private final HardwareRepo hardwareRepo;
    private final HardwareFeatureService hardwareFeatureService;
    private final AssemblyHardwareServiceImpl assemblyHardwareService;

    @Override
    public Iterable<HardwareEntity> getAll() {
        return hardwareRepo.findAll();
    }

    @Override
    public List<HardwareEntity> getAllByType(String type) {
        ArrayList<HardwareEntity> resList = new ArrayList<>();
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) hardwareRepo.findAll();
        for (HardwareEntity hardwareEntity : arrayList) {
            if (hardwareEntity.getType().getName().equals(type)) {
                resList.add(hardwareEntity);
            }
        }
        resList.sort(comparator);
        return resList;
    }
    @Override
    public List<HardwareEntity> getAllByType(int id) {

        ArrayList<HardwareEntity> resList = new ArrayList<>();
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) hardwareRepo.findAll();
        for (HardwareEntity hardwareEntity : arrayList) {
            if (hardwareEntity.getType().getId() == id) {
                resList.add(hardwareEntity);
            }
        }
        resList.sort(comparator);
        return resList;
    }

    @Override
    public List<HardwareEntity> getAllBySearching(ArrayList<HardwareEntity> inputList, String search, String type) {
        List<HardwareEntity> hardwares = getAllBySearching(search, type);
        hardwares.removeIf(hardware -> !inputList.contains(hardware));
        return hardwares;
    }

    @Override
    public List<HardwareEntity> getAllBySearching(String search, String type) {
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) getAllByType(type);
        arrayList.removeIf(
            elem -> !elem.getName().replaceAll(" ", "").toLowerCase().contains(
                search.replaceAll(" ", "").toLowerCase()
            )
        );
        return arrayList;
    }

    @Override
    public List<HardwareEntity> getAllByPrice(double minPrice, double maxPrice, String type) {
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) getAllByType(type);
        arrayList.removeIf(elem -> elem.getPrice() < minPrice || elem.getPrice() > maxPrice);
        return arrayList;
    }

    @Override
    public List<HardwareEntity> getAllByPrice(double price, Expression func, String type) {
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) getAllByType(type);
        arrayList.removeIf(elem -> func.isEqual(elem.getPrice(), price));
        return arrayList;
    }

    @Override
    public List<HardwareEntity> getAllByFeature(Hardware.Feature feature, String featureVal) {
        List<HardwareEntity> hardwareList = new ArrayList<>();
        List<HardwareFeature> featureList = (List<HardwareFeature>) hardwareFeatureService.getAllByNameAndValue(feature.getName(), featureVal);

        featureList.forEach(elem -> hardwareList.add(elem.getHardwareEntity()));
        return hardwareList;
    }

    @Override
    public List<HardwareEntity> getAllByFeature(String feature, String type) {
        List<HardwareEntity> hardwares = new ArrayList<>();
        List<HardwareFeature> featureList = hardwareFeatureService.getAllByNameAndHardwareType(
            feature.split("#")[0],
            type,
            this
        );
        featureList.removeIf(elem -> !elem.getValue().equals(feature.split("#")[1]));
        featureList.forEach(elem -> hardwares.add(elem.getHardwareEntity()));
        return hardwares;
    }

    @Override
    public List<HardwareEntity> filterByPrice(double minPrice, double maxPrice, String type) {
        List<HardwareEntity> hardwares;
        if (maxPrice != 0 && minPrice != 0) {
            hardwares = getAllByPrice(minPrice, maxPrice, type);
        } else if (maxPrice != 0) {
            hardwares = getAllByPrice(maxPrice, (p, p2) -> p > p2, type);
        } else if (minPrice != 0) {
            hardwares = getAllByPrice(minPrice, (p, p2) -> p < p2, type);
        } else {
            hardwares = getAllByType(type);
        }
        return hardwares;
    }

    @Override
    public List<HardwareEntity> filterByPrice(ArrayList<HardwareEntity> inputList, double minPrice, double maxPrice, String type) {
        List<HardwareEntity> hardwares = filterByPrice(minPrice, maxPrice, type);
        hardwares.removeIf(hardware -> !inputList.contains(hardware));
        return hardwares;
    }

    @Override
    public List<HardwareEntity> filterByFeatures(List<String> features, String type) {
        List<HardwareEntity> hardwares = new ArrayList<>();
        int count = 0;
        for (String feature: features) {
            if(!feature.isEmpty()) {
                hardwares.addAll(getAllByFeature(feature, type));
                count ++;
            }
        }

        if (count > 1) {
            hardwares.sort(comparator);
            if (new HashSet<>(hardwares).size() == hardwares.size()) {
                return new ArrayList<>();
            }
            for (int i = 1; i < hardwares.size(); ++i) {

                if (i == hardwares.size() - 1 && !hardwares.get(i).equals(hardwares.get(i - 1))) {
                    hardwares.remove(i);
                    i--;
                }
                if (
                    !hardwares.get(i).equals(hardwares.get(i - 1))
                        && !hardwares.get(i).equals(hardwares.get(i + 1))
                ) {
                    hardwares.remove(i);
                    i--;
                }
            }

            TreeSet<HardwareEntity> set = new TreeSet<>(comparator);
            set.addAll(hardwares);

            return new ArrayList<>(set);
        }

        return hardwares;
    }
    @Override
    public List<HardwareEntity> filterByFeatures(ArrayList<HardwareEntity> inputList, List<String> features, String type) {
        List<HardwareEntity> hardwares = filterByFeatures(features, type);
        hardwares.removeIf(hardware -> !inputList.contains(hardware));
        return hardwares;
    }

    @Override
    public List<HardwareEntity> filterByAll(double minPrice, double maxPrice, List<String> features, String type) {
        boolean isPrice = false;
        boolean isFilter = false;
        List<HardwareEntity> hardwareList = new ArrayList<>();
        List<HardwareEntity> hardwaresFilteredByPrice = new ArrayList<>();
        List<HardwareEntity> hardwaresFilteredByFeatures = new ArrayList<>();

        if (minPrice != 0 || maxPrice != 0) {
            isPrice = true;
            hardwaresFilteredByPrice = filterByPrice(minPrice, maxPrice, type);
        }

        if (!StringUtils.haveAllStrings("", features)) {
            isFilter = true;
            hardwaresFilteredByFeatures = filterByFeatures(features, type);
        }

        if (!hardwaresFilteredByPrice.isEmpty() && !hardwaresFilteredByFeatures.isEmpty()) {
            for (HardwareEntity hardware : hardwaresFilteredByPrice) {
                if (hardwaresFilteredByFeatures.contains(hardware)) {
                    hardwareList.add(hardware);
                }
            }
        } else if (!hardwaresFilteredByPrice.isEmpty() && !isFilter) {
            hardwaresFilteredByPrice.sort(comparator);
            return hardwaresFilteredByPrice;
        } else if (!hardwaresFilteredByFeatures.isEmpty() && !isPrice) {
            hardwaresFilteredByFeatures.sort(comparator);
            return hardwaresFilteredByFeatures;
        } else {
//            return getAllByType(type);
            return new ArrayList<>();
        }
        hardwareList.sort(comparator);
        return hardwareList;
    }

    @Override
    public List<HardwareEntity> filterByAll(ArrayList<HardwareEntity> inputList, double minPrice, double maxPrice, List<String> features, String type) {
        boolean isPrice = false;
        boolean isFilter = false;
        List<HardwareEntity> hardwareList = new ArrayList<>();
        List<HardwareEntity> hardwaresFilteredByPrice = new ArrayList<>();
        List<HardwareEntity> hardwaresFilteredByFeatures = new ArrayList<>();

        if (minPrice != 0 || maxPrice != 0) {
            isPrice = true;
            hardwaresFilteredByPrice = filterByPrice(inputList, minPrice, maxPrice, type);
        }

        if (!StringUtils.haveAllStrings("", features)) {
            isFilter = true;
            hardwaresFilteredByFeatures = filterByFeatures(inputList, features, type);
        }

        if (!hardwaresFilteredByPrice.isEmpty() && !hardwaresFilteredByFeatures.isEmpty()) {
            for (HardwareEntity hardware : hardwaresFilteredByPrice) {
                if (hardwaresFilteredByFeatures.contains(hardware)) {
                    hardwareList.add(hardware);
                }
            }
        } else if (!hardwaresFilteredByPrice.isEmpty() && !isFilter) {
            hardwaresFilteredByPrice.sort(comparator);
            return hardwaresFilteredByPrice;
        } else if (!hardwaresFilteredByFeatures.isEmpty() && !isPrice) {
            hardwaresFilteredByFeatures.sort(comparator);
            return hardwaresFilteredByFeatures;
        } else {
//            return getAllByType(type);
            return new ArrayList<>();
        }
        hardwareList.sort(comparator);
        return hardwareList;
    }

    @Override
    public void addHardware(HardwareEntity hardwareEntity) {
        ArrayList<HardwareEntity> hardwareListFromAssemble = assemblyHardwareService.getAllHardware();
        ArrayList<String> hardwareListNames = new ArrayList<>();
        hardwareListFromAssemble.forEach(el -> hardwareListNames.add(el.getName()));
        if (hardwareListNames.contains(hardwareEntity.getName())) {
            hardwareRepo.setHardwarePrice(findByName(hardwareEntity.getName()).getId(), hardwareEntity.getPrice());
        } else {
            this.hardwareRepo.save(hardwareEntity);
        }
    }

    @Override
    public void deleteAll() {
        ArrayList<HardwareEntity> hardwareList = (ArrayList<HardwareEntity>) getAll();
        ArrayList<HardwareEntity> hardwareListFromAssemble = assemblyHardwareService.getAllHardware();

        for (HardwareEntity hardware : hardwareList) {
            if (!hardwareListFromAssemble.contains(hardware)) {
                hardwareRepo.delete(hardware);
            }
        }
    }

    @Override
    public HardwareEntity findById(int id) {
        return hardwareRepo.findById(id).get();
    }

    @Override
    public HardwareEntity findByName(String name) {
        return hardwareRepo.findByName(name);
    }

    Comparator<HardwareEntity> comparator = (hardware1, hardware2) -> {
        Double o1 = hardware1.getPrice();
        Double o2 = hardware2.getPrice();
        return o1.compareTo(o2);
    };
}
