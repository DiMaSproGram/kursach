package app.service;

import app.entity.HardwareEntity;
import app.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface AssemblyService {
    void save(HardwareEntity[] arr, User user, double totalPrice);
    HashMap<String, ArrayList<HardwareEntity>> getByUser(int userId);
}
