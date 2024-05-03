package com.example.assignment;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class InstrumentTypeController implements Initializable {
    @FXML
    private Label lbID;
    @FXML
    private TableView listTable;
    @FXML
    private Button btnAdd, btnEdit;
    @FXML
    private TextField txtType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAll();
    }

    private void clearInput() {
        txtType.setText("");
    }

    private void resetForm() {
        clearInput();

        btnAdd.setText("Add");
        btnEdit.setDisable(true);
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");

        alert.setHeaderText(null);
        alert.setContentText("Instrument type is blank");

        alert.showAndWait();
    }

    @FXML void enterPress(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER)
            add();
    }

    @FXML
    protected void add() {
        if (btnAdd.getText().equals("Add")) {
            if (txtType.getText().isBlank()) {
                showError();
                txtType.requestFocus();
                return;
            }

            var type = new InstrumentType(0, txtType.getText());
            InstrumentTypeList.add(type);
            clearInput();

            return;
        }

        resetForm();
    }

    @FXML
    protected void edit() {
        if (txtType.getText().isBlank()) {
            showError();
            txtType.requestFocus();
            return;
        }

        var id = Integer.parseInt(lbID.getText());
        var type = new InstrumentType(id, txtType.getText());

        InstrumentTypeList.edit(type);

        resetForm();
    }

    private void viewDetail(InstrumentType instrumentType) {
        txtType.setText(instrumentType.getType());

        btnAdd.setText("Cancel");
        btnEdit.setDisable(false);

        lbID.setText(String.valueOf(instrumentType.getID()));
    }

    private void remove(InstrumentType instrumentType) {
        InstrumentTypeList.remove(instrumentType.getID());
        clearInput();
    }

    @FXML
    protected void viewAll() {
        TableColumn<InstrumentType, InstrumentType> colAction = new TableColumn<>("Action");
        colAction.setMinWidth(200);
        colAction.setStyle("-fx-alignment: CENTER;");
        colAction.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colAction.setCellFactory(param -> new TableCell<InstrumentType, InstrumentType>() {
            private final HBox hBox = new HBox();
            private final Button btnRemove = new Button("X");
            private final Button btnViewDetail = new Button("View");

            @Override
            protected void updateItem(InstrumentType instrumentType, boolean empty) {
                super.updateItem(instrumentType, empty);

                if (instrumentType == null) {
                    setGraphic(null);
                    return;
                }

                hBox.getChildren().clear();

                hBox.setSpacing(5);
                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().add(btnRemove);
                hBox.getChildren().add(btnViewDetail);

                setGraphic(hBox);

                btnRemove.setOnAction(event -> remove(instrumentType));
                btnViewDetail.setOnAction(event -> viewDetail(instrumentType));
            }
        });

        TableColumn<InstrumentType, String> colID = new TableColumn<>("ID");
        colID.setMinWidth(50);
        colID.setStyle("-fx-alignment: CENTER;");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<InstrumentType, String> colType = new TableColumn<>("Type");
        colType.setMinWidth(180);
        colType.setStyle("-fx-alignment: CENTER;");
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));

        listTable.setItems(InstrumentTypeList.getList());
        listTable.getColumns().addAll(colID, colType, colAction);
    }
}
