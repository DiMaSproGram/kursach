package app.service;

import app.entity.Hardware;
import app.entity.User;

import java.util.ArrayList;

public interface AssemblyService {
    void save(Hardware[] arr, User user, double totalPrice);
    ArrayList<ArrayList<Hardware>> getByUser(int userId);
}
