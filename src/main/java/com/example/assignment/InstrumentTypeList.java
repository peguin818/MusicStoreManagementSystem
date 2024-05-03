package com.example.assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class InstrumentTypeList {
    private final static String _FN_TYPES = "types.data";
    private static ObservableList<InstrumentType> _types = FXCollections.observableArrayList();
    private static int _IDCount = 0;

    public static int add(InstrumentType instrumentType) {
        if (instrumentType == null)
            return -1;

        instrumentType.setID(_IDCount);
        _types.add(instrumentType);
        _IDCount++;

        return 1;
    }

    public static InstrumentType find(int id) {
        return _types.stream().filter(p -> p.getID() == id).findFirst().get();
    }

    public static InstrumentType find(String name) {
        return _types.stream().filter(p -> p.getType().equals(name)).findFirst().get();
    }

    public static int edit(InstrumentType newType) {
        for (var type : _types) {
            if (type.getID() == newType.getID()) {
                var index = _types.indexOf(type);
                _types.set(index, newType);

                return 1;
            }
        }

        return -1;
    }

    public static int remove(int id) {
        for (var type : _types) {
            if (type.getID() == id) {
                _types.remove(type);
                return 1;
            }
        }

        return -1;
    }

    public static ObservableList<InstrumentType> getList() {
        return _types;
    }

    public static void readData() {
        try {
            File inFile = new File(_FN_TYPES);
            FileInputStream inFileStream = new FileInputStream(inFile);
            ObjectInputStream inObjectStream = new ObjectInputStream(inFileStream);

            while (true) {
                InstrumentType item = (InstrumentType) inObjectStream.readObject();
                _types.add(item);
                _IDCount++;
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
            File outFile = new File(_FN_TYPES);
            FileOutputStream outFileStream = new FileOutputStream(outFile);
            ObjectOutputStream outObjectStream = new ObjectOutputStream(outFileStream);

            for (var item : _types)
                outObjectStream.writeObject(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
