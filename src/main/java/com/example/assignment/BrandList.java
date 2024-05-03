package com.example.assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BrandList {
    private static ObservableList<String> _brands = FXCollections.observableArrayList
            ("Fender", "Squier", "Yamaha", "Ibanez", "Taylor");

    public static ObservableList<String> getList() {
        return _brands;
    }
}
