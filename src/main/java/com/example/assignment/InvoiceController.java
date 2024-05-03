package com.example.assignment;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {
    double remain = 0;
    double total = 0;
    @FXML
    private TextField txtPrice, txtQuantity, txtTotal, txtCash, txtRemain;
    @FXML
    private ComboBox cbInstrument;
    @FXML
    private Button btnAdd, btnPay;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //viewAll();

        cbInstrument.setItems(InstrumentList.getList());


        cbInstrument.valueProperty().addListener(new ChangeListener<Instrument>() {
            @Override
            public void changed(ObservableValue<? extends Instrument> observableValue, Instrument oldInstrument, Instrument newInstrument) {
                if (newInstrument != null && newInstrument != oldInstrument) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showPrice();
                        }
                    });
                }
            }
        });
    }

    @FXML
    public void showPrice() {
        Instrument instrument = (Instrument) cbInstrument.getValue();
        txtPrice.setText(String.valueOf(instrument.getPrice()));
    }

    private void clearInput() {
        cbInstrument.setValue(null);
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    @FXML
    private void resetForm() {
        clearInput();

        txtTotal.setText("");
        txtCash.setText("");
        txtRemain.setText("");
        btnAdd.setDisable(false);
        btnPay.setDisable(false);
    }

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");

        alert.setHeaderText(null);
        alert.setContentText(error);

        alert.showAndWait();
    }

    @FXML
    private void add() {
        if (cbInstrument.getValue() == null) {
            showError("No item is chosen");
            cbInstrument.requestFocus();
            return;
        }

        if (txtPrice.getText().isBlank()) {
            showError("Price is blank");
            txtPrice.requestFocus();
            return;
        }

        double price;
        var validPrice = 0;

        try {
            price = Double.parseDouble(txtPrice.getText());

            if (price <= validPrice) {
                showError("Price must be greater than 0");
                txtPrice.requestFocus();
                return;
            }
        } catch (Exception e) {
            showError("Price must be a numeric value");
            return;
        }

        if (txtQuantity.getText().isBlank()) {
            showError("Quantity is blank");
            txtQuantity.requestFocus();
            return;
        }

        int quantity;
        var validQuantity = 1;

        try {
            quantity = Integer.parseInt(txtQuantity.getText());

            if (quantity < validQuantity) {
                showError("Quantity must be 1 or greater");
                txtQuantity.requestFocus();
                return;
            }
        } catch (Exception e) {
            showError("Quantity must be an int & a numeric value");
        }

        addToTotal();

        clearInput();

        return;
    }

    private void addToTotal() {
        total = Integer.parseUnsignedInt(txtQuantity.getText()) * Double.parseDouble(txtPrice.getText()) + total;

        txtTotal.setText(String.valueOf(total));
    }

    @FXML
    private void pay() {
        if (txtTotal.getText().isBlank()) {
            showError("Total is blank");
            cbInstrument.requestFocus();
            return;
        }

        if (txtCash.getText().isBlank()) {
            showError("Cash is blank");
            txtCash.requestFocus();
            return;
        }

        double cash;
        var validCash = 0;

        try {
            cash = Double.parseDouble(txtCash.getText());

            if (cash <= validCash) {
                showError("Cash must be greater than 0");
                txtCash.requestFocus();
                return;
            }
        } catch (Exception e) {
            showError("Cash must be a numeric value");
        }

        if (Double.parseDouble(txtCash.getText()) < total) {
            showError("Cash is not enough");
            txtCash.requestFocus();
            return;
        }

        remain = Double.parseDouble(txtCash.getText()) - total;

        txtRemain.setText(String.valueOf(remain));

        btnAdd.setDisable(true);
        btnPay.setDisable(true);
    }
}
