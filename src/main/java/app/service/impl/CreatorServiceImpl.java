package app.service.impl;

import app.entity.*;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CreatorServiceImpl implements CreatorService {
    enum Goals {
        FOR_GAMES(new double[]{ 0.21, 0.4, 0.08, 0.08, 0.07, 0.05, 0.04, 0.04, 0.05}),
        FOR_WORK(new double[]{ 0.4, 0.21, 0.08, 0.07, 0.07, 0.05, 0.04, 0.03, 0.03});

        private double[] arr;
        Goals(double[] arr){
            this.arr = arr;
        }
    }
    @Autowired
    private HardwareService hardwareService;

    @Override
    public ArrayList<Hardware> create(double price, String goalS) {
        ArrayList<Hardware> arrayList = new ArrayList<>();
        Goals goal;
        if(goalS.equals("1"))
            goal = Goals.FOR_GAMES;
        else
            goal = Goals.FOR_WORK;

        for(int i = 0; i < goal.arr.length; ++i)
            arrayList.add(getHardware(price * goal.arr[i], i));
        return arrayList;
    }
    private Hardware getHardware(double referencePrice, int hardwareId){
        Iterable<Hardware> iterable;
        HashSet<Hardware> titleSet = new HashSet<>();
        double lowBound = 0.94;
        double upBound = 1.03;
        double tempPrice;

        for (int i = 0; i < 3; ++i) {
            iterable = hardwareService.getAllByType(hardwareId + 1);
            for (Hardware hardware : iterable) {
                tempPrice = hardware.getPrice();
                if (referencePrice <= tempPrice && i == 0) {
                    titleSet.add(hardware);
                    break;
                }
                if (tempPrice > referencePrice * upBound)
                    continue;
                if (tempPrice > referencePrice * lowBound)
                    titleSet.add(hardware);
            }
            if(!titleSet.isEmpty())
                break;
            lowBound -= 0.04;
            upBound += 0.02;
        }
        List<Hardware> list = new ArrayList<>(titleSet);
        System.out.println(list);
        return list.get((int) (Math.random() * (titleSet.size() - 1)));
    }
}
