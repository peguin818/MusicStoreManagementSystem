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

public class InstrumentController implements Initializable {
    @FXML
    private TableView listTable;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Label lbID;
    @FXML
    private ComboBox cbBrand, cbType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAll();

        cbType.setItems(InstrumentTypeList.getList());
        cbBrand.setItems(BrandList.getList());
    }

    private void clearInput() {
        txtName.setText("");
        txtPrice.setText("");
        cbType.setValue(null);
        cbBrand.setValue(null);

    }

    private void resetForm() {
        clearInput();

        btnAdd.setText("Add");
        btnEdit.setDisable(true);

        txtName.requestFocus();
    }

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");

        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    @FXML
    protected void onEnterPress(KeyEvent ke) throws ClassNotFoundException {
        if (ke.getCode() == KeyCode.ENTER)
            add();
    }

    @FXML
    protected void add() throws ClassNotFoundException {
        if (btnAdd.getText().equals("Add")) {
            if (txtName.getText().isBlank()) {
                showError("Name is blank");
                txtName.requestFocus();
                return;
            }

            if (cbType.getValue() == null) {
                showError("No type is chosen");
                cbType.requestFocus();
                return;
            }

            if (cbBrand.getValue() == null) {
                showError("No brand is chosen");
                cbBrand.requestFocus();
                return;
            }

            if (txtPrice.getText().isBlank()) {
                showError("Price is blank");
                txtPrice.requestFocus();
                return;
            }

            double price;
            var validPrice = 1;

            try {
                price = Double.parseDouble(txtPrice.getText());

                if (price < validPrice) {
                    showError("Price must be greater than 0");
                    txtPrice.requestFocus();
                    return;
                }
            } catch (Exception e) {
                showError("Price must be a numeric value");
                return;
            }

            var type = (InstrumentType) cbType.getValue();
            var instrument = new Instrument(
                    0,
                    txtName.getText(),
                    type.getID(),
                    Double.parseDouble(txtPrice.getText()),
                    cbBrand.getValue().toString());

            InstrumentList.add(instrument);

            clearInput();

            return;
        }

        clearInput();

        resetForm();
    }

    @FXML
    protected void edit() {
        if (txtName.getText().isBlank()) {
            showError("Name is blank");
            txtName.requestFocus();
            return;
        }

        if (cbType.getValue() == null) {
            showError("No type is chosen");
            cbType.requestFocus();
            return;
        }

        if (cbBrand.getValue() == null) {
            showError("No brand is chosen");
            cbBrand.requestFocus();
            return;
        }

        if (txtPrice.getText().isBlank()) {
            showError("Price is blank");
            txtPrice.requestFocus();
            return;
        }

        var id = Integer.parseInt(lbID.getText());
        var type = (InstrumentType) cbType.getValue();
        var instrument = new Instrument(
                id,
                txtName.getText(),
                type.getID(),
                Double.parseDouble(txtPrice.getText()),
                cbBrand.getValue().toString());

        InstrumentList.edit(instrument);

        resetForm();
    }

    private void viewDetail(Instrument instrument) {
        var type = InstrumentTypeList.find(instrument.getTypeID());

        txtName.setText(instrument.getName());
        cbType.setValue(type);
        cbBrand.setValue(instrument.getBrand());
        txtPrice.setText(instrument.getPrice().toString());

        btnAdd.setText("Cancel");
        btnEdit.setDisable(false);

        lbID.setText(String.valueOf(instrument.getID()));
    }

    private void remove(Instrument instrument) {
        InstrumentList.remove(instrument.getID());
        clearInput();
    }

    @FXML
    protected void viewAll() {
        TableColumn<Instrument, Instrument> colAction = new TableColumn<>("Action");
        colAction.setMinWidth(200);
        colAction.setStyle("-fx-alignment: CENTER;");
        colAction.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colAction.setCellFactory(param -> new TableCell<Instrument, Instrument>() {
            private final HBox hBox = new HBox();
            private final Button btnRemove = new Button("X");
            private final Button btnViewDetail = new Button("View");

            @Override
            protected void updateItem(Instrument instrument, boolean empty) {
                super.updateItem(instrument, empty);

                if (instrument == null) {
                    setGraphic(null);
                    return;
                }

                hBox.getChildren().clear();

                hBox.setSpacing(5);
                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().add(btnRemove);
                hBox.getChildren().add(btnViewDetail);

                setGraphic(hBox);

                btnRemove.setOnAction(event -> remove(instrument));
                btnViewDetail.setOnAction(event -> viewDetail(instrument));
            }
        });

        TableColumn<Instrument, String> colID = new TableColumn<>("ID");
        colID.setMinWidth(50);
        colID.setStyle("-fx-alignment: CENTER;");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Instrument, String> colName = new TableColumn<>("Name");
        colName.setMinWidth(50);
        colName.setStyle("-fx-alignment: CENTER;");
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        /*TableColumn<InstrumentType, String> colType = new TableColumn<>("Type");
        colType.setMinWidth(200);
        colType.setStyle("-fx-alignment: CENTER;");
        colType.setCellValueFactory(new PropertyValueFactory<InstrumentType, String>("Type"));*/

        TableColumn<Instrument, String> colBrand = new TableColumn<>("Brand");
        colBrand.setMinWidth(50);
        colBrand.setStyle("-fx-alignment: CENTER;");
        colBrand.setCellValueFactory(new PropertyValueFactory<>("Brand"));

        TableColumn<Instrument, String> colPrice = new TableColumn<>("Price");
        colPrice.setMinWidth(50);
        colPrice.setStyle("-fx-alignment: CENTER;");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        listTable.setItems(InstrumentList.getList());
        listTable.getColumns().addAll(colID, colName, colBrand, colPrice, colAction);
    }
}