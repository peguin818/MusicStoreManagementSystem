package com.example.assignment;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private Label lbID;
    @FXML
    private TableView listTable;
    @FXML
    private TextField txtUsername, txtPassword, txtName, txtPhone;
    @FXML
    private ComboBox cbRole;
    @FXML
    private Button btnAdd, btnEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAll();

        cbRole.setItems(FXCollections.observableArrayList(Role.Roles.values()));
        cbRole.setValue(Role.Roles.NONE);
    }

    private void clearInput() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtName.setText("");
        txtPhone.setText("");
        cbRole.setValue(Role.Roles.NONE);
    }

    private void resetForm() {
        clearInput();

        btnAdd.setText("Add");
        btnEdit.setDisable(true);

        txtUsername.setDisable(false);
        txtPassword.setDisable(false);
    }

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");

        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    @FXML
    protected void onEnterPress(KeyEvent ke) throws NoSuchAlgorithmException {
        if (ke.getCode() == KeyCode.ENTER)
            add();
    }

    @FXML
    protected void add() throws NoSuchAlgorithmException {
        if (btnAdd.getText().equals("Add")) {
            if (txtUsername.getText().isBlank()) {
                showError("Username is blank");
                txtUsername.requestFocus();
                return;
            }
            if (txtPassword.getText().isBlank()) {
                showError("Password is blank");
                txtPassword.requestFocus();
                return;
            }
            if (txtName.getText().isBlank()) {
                showError("Name is blank");
                txtName.requestFocus();
                return;
            }
            if (txtPhone.getText().isBlank()) {
                showError("Phone is blank");
                txtPhone.requestFocus();
                return;
            }

            User user = new User(0,
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtName.getText(),
                    txtPhone.getText(),
                    (Role.Roles) cbRole.getValue());

            UserList.add(user);

            clearInput();

            return;
        }

        resetForm();
    }

    @FXML
    protected void edit() throws NoSuchAlgorithmException {
        var id = Integer.parseInt(lbID.getText());
        User user = new User(id,
                txtUsername.getText(),
                txtPassword.getText(),
                txtName.getText(),
                txtPhone.getText(),
                (Role.Roles) cbRole.getValue());

        UserList.edit(user);

        resetForm();
    }

    private void viewDetail(User user) {
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtName.setText(user.getName());
        txtPhone.setText(user.getPhone());
        cbRole.valueProperty().set(user.getRole());

        btnAdd.setText("Cancel");
        btnEdit.setDisable(false);
        txtPassword.setDisable(true);
        txtUsername.setDisable(true);

        lbID.setText(String.valueOf(user.getID()));
    }

    private void remove(int id) {
        UserList.remove(id);
        clearInput();
    }

    @FXML
    protected void viewAll() {
        TableColumn<User, User> colAction = new TableColumn<>("Action");
        colAction.setMinWidth(200);
        colAction.setStyle("-fx-alignment: CENTER;");
        colAction.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colAction.setCellFactory(param -> new TableCell<User, User>() {
            private final HBox hBox = new HBox();
            private final Button btnRemove = new Button("X");
            private final Button btnViewDetail = new Button("View");

            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (user == null) {
                    setGraphic(null);
                    return;
                }

                hBox.getChildren().clear();

                hBox.setSpacing(5);
                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().add(btnRemove);
                hBox.getChildren().add(btnViewDetail);

                setGraphic(hBox);

                btnRemove.setOnAction(event -> remove(user.getID()));
                btnViewDetail.setOnAction(event -> viewDetail(user));
            }
        });

        TableColumn<User, String> colID = new TableColumn<>("ID");
        colID.setMinWidth(50);
        colID.setStyle("-fx-alignment: CENTER;");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<User, String> colUsername = new TableColumn<>("Username");
        colUsername.setMinWidth(100);
        colUsername.setStyle("-fx-alignment: CENTER;");
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));

        TableColumn<User, String> colName = new TableColumn<>("Name");
        colName.setMinWidth(150);
        colName.setStyle("-fx-alignment: CENTER;");
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<User, String> colPhone = new TableColumn<>("Phone");
        colPhone.setMinWidth(150);
        colPhone.setStyle("-fx-alignment: CENTER;");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        listTable.setItems(UserList.getList());
        listTable.getColumns().addAll(colID, colUsername, colName, colPhone, colAction);
    }
}
