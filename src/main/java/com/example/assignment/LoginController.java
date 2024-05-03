package com.example.assignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML
    private Pane mainPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void loginEnter(KeyEvent ke) throws IOException, NoSuchAlgorithmException {
        if (ke.getCode() == KeyCode.ENTER)
            login();
    }

    @FXML
    protected void login() throws IOException, NoSuchAlgorithmException {

        var username = txtUsername.getText();
        var password = txtPassword.getText();

        if (username.isBlank()) {
            showError("Username is blank.");
            txtUsername.requestFocus();
            return;
        }

        if (password.isBlank()) {
            showError("Password is blank.");
            txtPassword.requestFocus();
            return;
        }

        if (!UserList.authenticate(username, password)) {
            showError("Wrong username/password");
            return;
        }

        var user = UserList.find(username);

        switch (user.getRole()) {
            case STAFF -> loadUI("assignment-view", user);
            case ADMIN -> loadUI("assignmentAdmin-view", user);
            case NONE -> showError("No Role Found");
        }


    }

    private void loadUI(String fileName, User user) throws IOException {
        UserList.setCurrentUser(user);

        mainPane.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(Assignment.class.getResource(fileName + ".fxml"));
        mainPane.getChildren().add(newLoadedPane);
    }

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");

        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }
}
