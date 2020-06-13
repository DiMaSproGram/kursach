package app.service;

import app.entity.HardwareEntity;
import app.payload.Feature;
import app.payload.Hardware;
import app.payload.InputRanges;

import java.util.ArrayList;

public interface CreatorService {
    ArrayList<HardwareEntity> create(double price, String goal,boolean isSSD);
    ArrayList<HardwareEntity> create(
        double price,
        String goal,
        boolean isSSD,
        ArrayList<Integer> range,
        ArrayList<Integer> hardwareNumList,
        ArrayList<HardwareEntity> hardwareEntityList,
        ArrayList<Hardware.Feature> featureNameList,
        ArrayList<Feature> featuresList
    );
}
