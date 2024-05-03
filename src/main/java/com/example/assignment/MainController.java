package com.example.assignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Pane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            try {
                readData();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            mainPane.getChildren().clear();
            Pane newLoadedPane = FXMLLoader.load(Assignment.class.getResource("login-view.fxml"));
            mainPane.getChildren().add(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readData() throws NoSuchAlgorithmException {
        UserList.readData();
        InstrumentList.readData();
        InstrumentTypeList.readData();
    }
}
