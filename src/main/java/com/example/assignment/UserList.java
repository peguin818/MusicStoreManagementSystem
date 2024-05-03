package com.example.assignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class UserList {
    private final static String _FN_USERS = "users.data";
    private static ObservableList<User> _users = FXCollections.observableArrayList();
    private static User _currentUser = null;
    private static int _IDCount = 0;

    public static int add(User user) {
        if (user == null)
            return -1;

        user.setID(_IDCount);
        _users.add(user);
        ++_IDCount;

        return 1;
    }

    public static User find(int id) {
        return _users.stream().filter(u -> u.getID() == id).findFirst().get();
    }

    public static User find(String username) {
        return _users.stream().filter(u -> u.getUsername().equals(username)).findFirst().get();
    }

    public static boolean authenticate(String username, String password) {
        for (var staff : _users) {
            if (staff.getUsername().equals(username) && staff.matchPassword(password))
                return true;
        }

        return false;
    }

    public static User getCurrentUser() {
        return _currentUser;
    }

    public static void setCurrentUser(User user) {
        _currentUser = user;
    }

    public static Role.Roles getRole(String username) {
        for (var user : _users) {
            if (user.getUsername().equals(username))
                return user.getRole();
        }

        return null;
    }

    public static int edit(User newUser) {
        for (var staff : _users) {
            if (staff.getID() == newUser.getID()) {
                var index = _users.indexOf(staff);

                staff.update(
                        newUser.getName(),
                        newUser.getPhone(),
                        newUser.getRole()
                );

                _users.set(index, staff);

                return 1;
            }
        }

        return -1;
    }

    public static int remove(int id) {
        for (var staff : _users) {
            if (staff.getID() == id) {
                _users.remove(staff);
                return 1;
            }
        }

        return -1;
    }

    public static ObservableList<User> getList() {
        return _users;
    }

    public static ObservableList<User> getAdmins() {
        return _users.filtered(u -> u.getRole() == Role.Roles.ADMIN);
    }

    public static void readData() throws NoSuchAlgorithmException {
        try {
            File inFile = new File(_FN_USERS);
            FileInputStream inFileStream = new FileInputStream(inFile);
            ObjectInputStream inObjectStream = new ObjectInputStream(inFileStream);

            while (true) {
                User item = (User) inObjectStream.readObject();
                _users.add(item);
                _IDCount++;
            }
        } catch (EOFException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveData() {
        try {
            File outFile = new File(_FN_USERS);
            FileOutputStream outFileStream = new FileOutputStream(outFile);
            ObjectOutputStream outObjectStream = new ObjectOutputStream(outFileStream);

            for (var item : _users)
                outObjectStream.writeObject(item);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
