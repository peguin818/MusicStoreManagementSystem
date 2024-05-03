package com.example.assignment;

import java.io.Serializable;

public class InstrumentType implements Serializable {
    private int _id;
    private String _type;

    public InstrumentType() {
        setInfo(-1, "");
    }

    public InstrumentType(int id, String type) {
        setInfo(id, type);
    }

    private void setInfo(int id, String type) {
        _id = id;
        _type = type;
    }

    public void update(String type) {
        setInfo(_id, type);
    }

    public void setID(int id) {
        _id = id;
    }

    public int getID() {
        return _id;
    }

    public void setType(String type) {
        _type = type;
    }

    public String getType() {
        return _type;
    }

    public String toString() {
        return _type;
    }
}
