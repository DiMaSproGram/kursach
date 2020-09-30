package app.service.impl;

import app.common.StringUtils;
import app.common.service.AbstractService;
import app.payload.Feature;
import app.payload.Hardware;
import app.entity.*;
import app.payload.InputRanges;
import app.repository.AssemblyRepo;
import app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CreatorService {

    private final HardwareService hardwareService;
    private final HardwareFeatureService hardwareFeatureService;
    private ArrayList<HardwareEntity> hardwareList;

    public ArrayList<HardwareEntity> create(
        double price,
        String goal,
        boolean isSSD,
        ArrayList<Integer> range,
        ArrayList<Integer> hardwareNumList,
        ArrayList<HardwareEntity> hardwareEntityList,
        ArrayList<Hardware.Feature> featureNameList,
        ArrayList<Feature> featuresList
    ) {
        hardwareList = hardwareEntityList;
        Goals goals = goal.equals("1")
            ? Goals.FOR_GAMES
            : Goals.FOR_WORK;
        LinkedList<HardwareEntity> linkedList = new LinkedList<>(hardwareEntityList);

        for (int i = 0; i < hardwareNumList.size(); ++i) {
            if (
                !range.get(i).equals(
                    hardwareNumList.get(i)
                )
            ) {
                if (
                    getHardware(
                        getHardwareName(featureNameList.get(i)),
                        featureNameList.get(i),
                        getFeatureValue(featuresList, featureNameList.get(i), range.get(i))
                    )
                    == null
                ) {
                    return create(
                        hardwareService.getAllByFeature(
                            featureNameList.get(i),
                            getFeatureValue(featuresList, featureNameList.get(i), range.get(i))
                        ).get(0).getPrice() / goals.arr[getHardwareListPosition(hardwareEntityList, featureNameList.get(i))],
                        goal,
                        isSSD
                    );
                }

                linkedList.remove(
                    getHardwareListPosition(
                        hardwareEntityList,
                        featureNameList.get(i)
                    )
                );
                linkedList.add(
                    getHardwareListPosition(
                        hardwareEntityList,
                        featureNameList.get(i)
                    ),
                    getHardware(
                        getHardwareName(featureNameList.get(i)),
                        featureNameList.get(i),
                        getFeatureValue(featuresList, featureNameList.get(i), range.get(i))
                    )
                );
            }
        }

        return new ArrayList<>(linkedList);
    }

    public ArrayList<HardwareEntity> create(double price, String goal, boolean isSSD) {
        hardwareList = new ArrayList<>();
        Goals goals = goal.equals("1")
            ? Goals.FOR_GAMES
            : Goals.FOR_WORK;
        int i = 0;
        double bonusCoeff = 0;

        for (Hardware hardware : Hardware.values()) {
            if(!isSSD && !(hardware == Hardware.SSD)) {
                if (
                    goals == Goals.FOR_GAMES && hardware == Hardware.VIDEO_CARD
                    || goals == Goals.FOR_WORK && hardware == Hardware.PROCESSOR
                ) {
                    bonusCoeff = goals.arr[5];
                }
            }
            if (
                hardware == Hardware.COOLERS
                && hardwareFeatureService.getByHardwareIdAndName(
                        hardwareList.get(0).getId(),
                        Hardware.Feature.BOX.getName()
                    )
                    .getValue()
                    .equals("true")
            ) {
                bonusCoeff = goals.arr[i];
                continue;
            }
            hardwareList.add(
                getHardware(
                    price * (goals.arr[i] + bonusCoeff),
                    hardware
                )
            );
            i++;
            bonusCoeff = 0;
        }

        return hardwareList;
    }

    private boolean checkCompatibility(HardwareEntity entity, Hardware hardware) {
        if (
            hardware.getFeature().contains(Hardware.Feature.SOCKET)
            && hardwareList.size() >= 1
        ) {
            if (hardware == Hardware.COOLERS) {
                ArrayList<String> arr = new ArrayList<>(
                    Arrays.asList(
                        hardwareFeatureService
                            .getByHardwareIdAndName(
                                entity.getId(),
                                Hardware.Feature.SOCKET.getName()
                            )
                            .getValue()
                            .split(", ")
                    )
                );

                return arr.contains(
                    hardwareFeatureService.getByHardwareIdAndName(
                        hardwareList.get(0).getId(),
                        Hardware.Feature.SOCKET.getName()
                    ).getValue()
                );
            }
            return hardwareFeatureService.getByHardwareIdAndName(
                    hardwareList.get(0).getId(),
                    Hardware.Feature.SOCKET.getName()
                ).getValue()
                .equals(
                    hardwareFeatureService.getByHardwareIdAndName(
                        entity.getId(),
                        Hardware.Feature.SOCKET.getName()
                    ).getValue()
                );
        }
        if (
            hardware.getFeature().contains(Hardware.Feature.RECOMMEND_WATT)
            && hardwareList.size() > 2
        ) {
            return Integer.parseInt(
                StringUtils.trimRus(
                    hardwareFeatureService
                        .getByHardwareIdAndName(
                            entity.getId(),
                            Hardware.Feature.RECOMMEND_WATT.getName()
                        )
                        .getValue()
                )
            ) <= Integer.parseInt(
                StringUtils.trimRus(
                    hardwareFeatureService
                        .getByHardwareIdAndName(
                            hardwareList.get(getHardwarePosition(Hardware.Feature.WATT)).getId(),
                            Hardware.Feature.WATT.getName()
                        )
                        .getValue()
                )
            );
        }
        if (hardware.getFeature().contains(Hardware.Feature.WATT)) {
            return Integer.parseInt(
                StringUtils.trimRus(
                    hardwareFeatureService
                        .getByHardwareIdAndName(
                            hardwareList.get(1).getId(),
                            Hardware.Feature.RECOMMEND_WATT.getName()
                        )
                        .getValue()
                )
            ) <= Integer.parseInt(
                StringUtils.trimRus(
                    hardwareFeatureService
                        .getByHardwareIdAndName(
                            entity.getId(),
                            Hardware.Feature.WATT.getName()
                        )
                        .getValue()
                )
            );
        }
        return true;
    }

    private HardwareEntity getHardware(Hardware hardware, Hardware.Feature feature, String featureVal){
        List<HardwareEntity> list = hardwareService.getAllByFeature(feature, featureVal);

        for (HardwareEntity hardwareEntity : list) {
            if (
                hardwareFeatureService.getByHardwareIdAndName(hardwareEntity.getId(), feature.getName()).getValue()
                    .equals(featureVal)
                && checkCompatibility(hardwareEntity, hardware)
            ) {
                return hardwareEntity;
            }
        }

        return null;
    }

    private HardwareEntity getHardware(double referencePrice, Hardware hardware){
        List<HardwareEntity> hardwareList = hardwareService.getAllByType(hardware.ordinal() + 1);
        HashSet<HardwareEntity> titleSet = new HashSet<>();
        double lowBound = 0.96;
        double upBound = 1.02;
        double tempPrice;

        for (int i = 0; i < 6; ++i) {
            for (HardwareEntity hardwareEntity : hardwareList) {
                tempPrice = hardwareEntity.getPrice();
                if (
                    referencePrice <= tempPrice
                    && checkCompatibility(hardwareEntity, hardware)
                ) {
                    titleSet.add(hardwareEntity);
                    break;
                }
                if (tempPrice > referencePrice * upBound)
                    continue;
                if (tempPrice > referencePrice * lowBound)
                    titleSet.add(hardwareEntity);
            }
            if (!titleSet.isEmpty())
                break;
            lowBound -= 0.03;
            upBound += 0.01;
            if (i == 5) {
                titleSet.add(hardwareList.get(hardwareList.size() - 1));
            }
        }
        List<HardwareEntity> list = new ArrayList<>(titleSet);

        return list.get((int) (Math.random() * (titleSet.size() - 1)));
    }

    private Hardware getHardwareName(Hardware.Feature feature) {
        for (Hardware hardware : Hardware.values()) {
            if (hardware.getFeature().contains(feature)) {
                return hardware;
            }
        }
        return null;
    }

    private int getHardwareListPosition(ArrayList<HardwareEntity> hardwareEntityList, Hardware.Feature feature) {
        for (int i = 0; i < hardwareEntityList.size(); ++i) {
            if (
                hardwareFeatureService.getByHardwareEntityId(hardwareEntityList.get(i).getId())
                    .contains(hardwareFeatureService.getByHardwareIdAndName(
                        hardwareEntityList.get(i).getId(), feature.getName())
                    )
            ) {
                return i;
            }
        }
        return -1;
    }

    private int getHardwarePosition(Hardware.Feature feature) {
        int i = 0;
        for (Hardware hardware : Hardware.values()) {
            if (hardware.getFeature().contains(feature)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private String getFeatureValue(ArrayList<Feature> featuresList, Hardware.Feature feature, int position) {
        int i = 0;
        for (Feature feature1 : featuresList) {
            if (feature1.getFeatureName().equals(feature.getName())) {
                for (String val : feature1.getFeatureValues()) {
                    if (i == position) {
                        return val;
                    }
                    i++;
                }
            }
        }
        return null;
    }

    enum Goals {
        FOR_GAMES(new double[]{ 0.21, 0.4, 0.08, 0.08, 0.07, 0.05, 0.04, 0.04, 0.05}),
        FOR_WORK(new double[]{ 0.4, 0.21, 0.08, 0.07, 0.07, 0.05, 0.04, 0.03, 0.03});

        private double[] arr;
        Goals(double[] arr){
            this.arr = arr;
        }
    }
}
