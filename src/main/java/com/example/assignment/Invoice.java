package com.example.assignment;

import java.io.Serializable;

public class Invoice implements Serializable {
    private int _instrumentID;

    public Invoice(int instrumentID) {
        setInfo(instrumentID);
    }

    private void setInfo(int instrumentID) {
        _instrumentID = instrumentID;
    }

    public int getInstrumentID() {
        return _instrumentID;
    }

    public void setInstrumentID(int instrumentID) {
        _instrumentID = instrumentID;
    }
}
