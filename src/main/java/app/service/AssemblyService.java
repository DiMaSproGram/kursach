package app.service;

import app.entity.Assembly;
import app.entity.Hardware;
import app.entity.User;

public interface AssemblyService {
    void save(Hardware[] arr, User user, double totalPrice);
    Iterable<Assembly> getByUser(int userId);
}
