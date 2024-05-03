package com.example.assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class InstrumentList {
    private final static String _FN_INSTRUMENTS = "instruments.data";
    private static ObservableList<Instrument> _instruments = FXCollections.observableArrayList();
    private static int _currentID = 0;

    public static Instrument find(int id) {
        return _instruments.stream().filter(p -> p.getID() == id).findFirst().get();
    }

    public static Instrument find(String name) {
        return _instruments.stream().filter(p -> p.getName().equals(name)).findFirst().get();
    }

    public static int add(Instrument instrument) {
        if (instrument == null)
            return -1;

        instrument.setID(_currentID);
        _instruments.add(instrument);
        _currentID++;

        return 1;
    }

    public static int edit(Instrument newInstrument) {
        for (var electricGuitar : _instruments) {
            if (electricGuitar.getID() == newInstrument.getID()) {
                var index = _instruments.indexOf(electricGuitar);
                _instruments.set(index, newInstrument);

                return 1;
            }
        }

        return -1;
    }

    public static int remove(int id) {
        for (var electricGuitar : _instruments) {
            if (electricGuitar.getID() == id) {
                _instruments.remove(electricGuitar);
                return 1;
            }
        }
        return -1;
    }

    public static ObservableList<Instrument> getList() {
        return _instruments;
    }

    public static void readData() {
        try {
            File inFile = new File(_FN_INSTRUMENTS);
            FileInputStream inFileStream = new FileInputStream(inFile);
            ObjectInputStream inObjectStream = new ObjectInputStream(inFileStream);

            while (true) {
                Instrument item = (Instrument) inObjectStream.readObject();
                _instruments.add(item);
                _currentID++;
            }
        } catch (EOFException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveData() {
        try {
            File outFile = new File(_FN_INSTRUMENTS);
            FileOutputStream outFileStream = new FileOutputStream(outFile);
            ObjectOutputStream outObjectStream = new ObjectOutputStream(outFileStream);

            for (var item : _instruments)
                outObjectStream.writeObject(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
