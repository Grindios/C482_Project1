package View_controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.Inventory;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPart implements Initializable{

    @FXML
    private RadioButton radioAddPartsInHouse;
    @FXML
    private RadioButton radioAddPartsOutsource;
    @FXML
    private Label lblAddPartsIDNumber;
    @FXML
    private TextField txtAddPartsName;
    @FXML
    private TextField txtAddPartsInv;
    @FXML
    private TextField txtAddPartsPrice;
    @FXML
    private TextField txtAddPartsMin;
    @FXML
    private TextField txtAddPartsMax;
    @FXML
    private Label lblAddPartsDyn;
    @FXML
    private TextField txtAddPartsDyn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        

    }
}
