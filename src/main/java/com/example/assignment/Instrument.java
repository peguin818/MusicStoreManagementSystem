package com.example.assignment;

import java.io.Serializable;

public class Instrument implements Serializable {
    private int _typeID;
    private String _name;
    private Double _price;
    private int _id;
    private String _brand;

    public Instrument() {
        setData(-1, "", -1, 0, "");
    }

    public Instrument(int id, String name, int typeID, double price, String brand) {
        setData(id, name, typeID, price, brand);
    }

    private void setData(int id, String name, int typeID, double price, String brand) {
        _id = id;
        _name = name;
        _typeID = typeID;
        _price = price;
        _brand = brand;
    }

    public void update(String name, int typeID, double price, String brand) {
        setData(_id, name, typeID, price, brand);
    }

    public int getID() {
        return _id;
    }

    public void setID(int id) {
        _id = id;
    }

    public Double getPrice() {
        return _price;
    }

    public void setPrice(Double price) {
        _price = price;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getBrand() {
        return _brand;
    }

    public void setBrand(String brand) {
        _brand = brand;
    }

    public int getTypeID() {
        return _typeID;
    }

    public void setTypeID(int typeID) {
        _typeID = typeID;
    }

    public String toString() {
        return _brand + " " + _name;
    }
}