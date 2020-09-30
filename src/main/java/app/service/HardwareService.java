package app.service;

import app.common.Expression;
import app.common.StringUtils;
import app.common.service.AbstractService;
import app.entity.HardwareEntity;
import app.entity.HardwareFeature;
import app.payload.Hardware;
import app.payload.Pagination;
import app.repository.HardwareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class HardwareService extends AbstractService<HardwareEntity, HardwareRepo> {

    private final HardwareFeatureService hardwareFeatureService;
    private final AssemblyHardwareService assemblyHardwareService;

    @Autowired
    public HardwareService(
        HardwareRepo repository,
        HardwareFeatureService hardwareFeatureService,
        AssemblyHardwareService assemblyHardwareService
    ) {
        super(repository);
        this.hardwareFeatureService = hardwareFeatureService;
        this.assemblyHardwareService = assemblyHardwareService;
    }

    public List<HardwareEntity> getAllByType(String type) {
        ArrayList<HardwareEntity> resList = new ArrayList<>();
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) repository.findAll();
        for (HardwareEntity hardwareEntity : arrayList) {
            if (hardwareEntity.getType().getName().equals(type)) {
                resList.add(hardwareEntity);
            }
        }
        resList.sort(comparator);
        return resList;
    }

    public List<HardwareEntity> getAllByType(int id) {

        ArrayList<HardwareEntity> resList = new ArrayList<>();
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) repository.findAll();
        for (HardwareEntity hardwareEntity : arrayList) {
            if (hardwareEntity.getType().getId() == id) {
                resList.add(hardwareEntity);
            }
        }
        resList.sort(comparator);
        return resList;
    }

    public List<HardwareEntity> getAllBySearching(
        ArrayList<HardwareEntity> inputList,
        String search,
        String type,
        Model model
    ) {
        if (search != null && !search.isEmpty()) {
            List<HardwareEntity> hardwares = getAllBySearching(search, type);
            hardwares.removeIf(hardware -> !inputList.contains(hardware));
            return hardwares;
        }
        return inputList;
    }

    public List<HardwareEntity> getAllBySearching(String search, String type) {
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) getAllByType(type);
        arrayList.removeIf(
            elem -> !elem.getName().replaceAll(" ", "").toLowerCase().contains(
                search.replaceAll(" ", "").toLowerCase()
            )
        );
        return arrayList;
    }

    public List<HardwareEntity> getAllByPrice(double minPrice, double maxPrice, String type) {
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) getAllByType(type);
        arrayList.removeIf(elem -> elem.getPrice() < minPrice || elem.getPrice() > maxPrice);
        return arrayList;
    }

    public List<HardwareEntity> getAllByPrice(double price, Expression func, String type) {
        ArrayList<HardwareEntity> arrayList = (ArrayList<HardwareEntity>) getAllByType(type);
        arrayList.removeIf(elem -> func.isEqual(elem.getPrice(), price));
        return arrayList;
    }

    public List<HardwareEntity> getAllByFeature(Hardware.Feature feature, String featureVal) {
        List<HardwareEntity> hardwareList = new ArrayList<>();
        List<HardwareFeature> featureList = (List<HardwareFeature>) hardwareFeatureService.getAllByNameAndValue(
            feature.getName(),
            featureVal
        );

        featureList.forEach(elem -> hardwareList.add(elem.getHardwareEntity()));
        return hardwareList;
    }

    public List<HardwareEntity> getAllByFeature(String feature, String type) {
        List<HardwareEntity> hardwares = new ArrayList<>();
        List<HardwareFeature> featureList = hardwareFeatureService.getAllByNameAndHardwareType(
            feature.split("#")[0],
            type,
            this
        );
        featureList.removeIf(elem -> !elem.getValue().contains(feature.split("#")[1]));
        featureList.forEach(elem -> hardwares.add(elem.getHardwareEntity()));
        return hardwares;
    }

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

    public List<HardwareEntity> filterByPrice(
        ArrayList<HardwareEntity> inputList,
        double minPrice,
        double maxPrice,
        String type
    ) {
        List<HardwareEntity> hardwares = filterByPrice(minPrice, maxPrice, type);
        hardwares.removeIf(hardware -> !inputList.contains(hardware));
        return hardwares;
    }

    public List<HardwareEntity> filterByFeatures(List<String> features, String type) {
        List<HardwareEntity> hardwares = new ArrayList<>();
        int count = 0;
        for (String feature : features) {
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

    public List<HardwareEntity> filterByFeatures(
        ArrayList<HardwareEntity> inputList,
        List<String> features,
        String type
    ) {
        List<HardwareEntity> hardwares = filterByFeatures(features, type);
        hardwares.removeIf(hardware -> !inputList.contains(hardware));
        return hardwares;
    }

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
            return new ArrayList<>();
        }
        hardwareList.sort(comparator);
        return hardwareList;
    }

    public List<HardwareEntity> filterByAll(
        ArrayList<HardwareEntity> inputList,
        double minPrice,
        double maxPrice,
        List<String> features,
        String type
    ) {
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
            return new ArrayList<>();
        }
        hardwareList.sort(comparator);
        return hardwareList;
    }

    public void addHardware(HardwareEntity hardwareEntity) {
        ArrayList<HardwareEntity> hardwareListFromAssemble = assemblyHardwareService.getAllHardware();
        ArrayList<String> hardwareListNames = new ArrayList<>();
        hardwareListFromAssemble.forEach(el -> hardwareListNames.add(el.getName()));
        if (hardwareListNames.contains(hardwareEntity.getName())) {
            repository.setHardwarePrice(findByName(hardwareEntity.getName()).getId(), hardwareEntity.getPrice());
        } else {
            this.repository.save(hardwareEntity);
        }
    }

    public void deleteAll() {
        ArrayList<HardwareEntity> hardwareList = (ArrayList<HardwareEntity>) repository.findAll();
        ArrayList<HardwareEntity> hardwareListFromAssemble = assemblyHardwareService.getAllHardware();

        for (HardwareEntity hardware : hardwareList) {
            if (!hardwareListFromAssemble.contains(hardware)) {
                repository.delete(hardware);
            }
        }
    }

    public HardwareEntity findByName(String name) {
        return repository.findByName(name);
    }

    public void fillPaginationList(
        String type,
        ArrayList<ArrayList<HardwareEntity>> paginationList,
        ArrayList<Pagination> pagesMap
    ) {
        List<HardwareEntity> hardwareList = getAllByType(type);
        paginationList.clear();
        pagesMap.clear();
        ArrayList<HardwareEntity> tempList = new ArrayList<>();

        for (int i = 0; i < hardwareList.size(); ++i){
            if (i > 19 && i % 20 == 0) {
                paginationList.add(new ArrayList<>(tempList));
                pagesMap.add(
                    new Pagination(
                        "/catalog/" + type + "/" + paginationList.size(),
                        paginationList.size()
                    )
                );
                tempList.clear();
            }
            tempList.add(hardwareList.get(i));
        }
        if (!tempList.isEmpty()) {
            paginationList.add(new ArrayList<>(tempList));
            pagesMap.add(
                new Pagination(
                    "/catalog/" + type + "/" + paginationList.size(),
                    paginationList.size()
                )
            );
        }
    }

    Comparator<HardwareEntity> comparator = (hardware1, hardware2) -> {
        Double o1 = hardware1.getPrice();
        Double o2 = hardware2.getPrice();
        return o1.compareTo(o2);
    };
}
