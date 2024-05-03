package com.example.assignment;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
    private int _id;
    private String _username;
    private String _password;
    private String _name;
    private String _phone;
    private Role.Roles _role;

    public User() throws NoSuchAlgorithmException {
        setInfo(-1, "", "", "", "", Role.Roles.NONE);
    }

    public User(int id, String username, String password, Role.Roles role) throws NoSuchAlgorithmException {
        setInfo(id, username, password, "", "", role);
    }

    public User(int id, String username, String password, String name, String phone, Role.Roles role) throws NoSuchAlgorithmException {
        setInfo(id, username, password, name, phone, role);
    }

    private void setInfo(int id, String username, String password, String name, String phone, Role.Roles role) throws NoSuchAlgorithmException {
        _id = id;
        _username = username;
        _password = password;
        _name = name;
        _phone = phone;
        _role = role;
    }

    public void update(String name, String phone, Role.Roles role) {
        _name = name;
        _phone = phone;
        _role = role;
    }

    /*private String hashString(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(text.getBytes());
        return new String(messageDigest.digest());
    }*/

    public boolean matchPassword(String password) {
        if (_password.equals(password))
            return true;

        return false;
    }

    public void setID(int id) {
        _id = id;
    }

    public int getID() {
        return _id;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public String getUsername() {
        return _username;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        _password = password;
    }

    public String getPassword() {
        return _password;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setPhone(String phone) {
        _phone = phone;
    }

    public String getPhone() {
        return _phone;
    }

    public void setRole(Role.Roles role) {
        _role = role;
    }

    public Role.Roles getRole() {
        return _role;
    }

    public String toString() {
        return _username;
    }
}
