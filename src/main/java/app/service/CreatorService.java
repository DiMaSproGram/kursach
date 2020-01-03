package app.service;

import app.entity.Hardware;

import java.util.ArrayList;

public interface CreatorService {
    ArrayList<Hardware> create(double price, String goal);
}
