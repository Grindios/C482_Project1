package View_controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Parts;
import model.Inventory;
import model.InHouse;
import model.Outsourced;
import model.Products;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPart implements Initializable{

    @FXML
    private RadioButton addPartsInHouseRdBtn;
    @FXML
    private RadioButton addPartsOutsourceRdBtn;
    @FXML
    private Label addPartsIDNumberLbl;
    @FXML
    private TextField addPartsNametxt;
    @FXML
    private TextField addPartsInStocktxt;
    @FXML
    private TextField addPartsPricetxt;
    @FXML
    private TextField addPartsMintxt;
    @FXML
    private TextField addPartsMaxtxt;
    @FXML
    private Label addPartsBoolLbl;
    @FXML
    private TextField addPartsBooltxt;
    @FXML
    private Button addPartsSaveBtn;

    // Buttons?

    private boolean isOutsourced;
    private String catchMessage = new String();
    private int partID;


    @FXML
    public void selectAddPartInHouse(javafx.event.ActionEvent event) {
        isOutsourced = false;
        addPartsBoolLbl.setText("Machine ID");
        addPartsBooltxt.setPromptText("Machine ID");
        addPartsOutsourceRdBtn.setSelected(false);
    }

    @FXML
    public void setAddPartsOutsourceRdBtn(javafx.event.ActionEvent event) {
        isOutsourced = true;
        addPartsBoolLbl.setText("Company Name");
        addPartsBooltxt.setPromptText("Company Name");
        addPartsOutsourceRdBtn.setSelected(true);
    }
    //Add parts
    @FXML
    public void SaveAddPartsAct(javafx.event.ActionEvent event) throws IOException {
        String partName = addPartsNametxt.getText();
        String partInStock = addPartsInStocktxt.getText();
        String partPrice = addPartsPricetxt.getText();
        String partMin = addPartsMintxt.getText();
        String partMax = addPartsMaxtxt.getText();
        String partBool = addPartsBooltxt.getText();

        try {
            catchMessage = model.Parts.getPartValidation(partName,
                    Integer.parseInt(partInStock),
                    Double.parseDouble(partPrice),
                    Integer.parseInt(partMax),
                    Integer.parseInt(partMin)
                    , catchMessage);
            if (catchMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding Part");
                alert.setContentText(catchMessage);
                alert.showAndWait();
                catchMessage = "";
            } else {
                if (isOutsourced == false) {
                    System.out.println("Part name: " + partName);
                    InHouse inPart = new InHouse();
                    inPart.setPartID(partID);
                    inPart.setPartName(partName);
                    inPart.setPartPrice(Double.parseDouble(partPrice));
                    inPart.setPartInStock(Integer.parseInt(partInStock));
                    inPart.setMin(Integer.parseInt(partMin));
                    inPart.setMax(Integer.parseInt(partMax));
                    inPart.setMachineID(Integer.parseInt(partBool));
                    Inventory.addPart(inPart);
                } else {
                    System.out.println("Part name: " + partName);
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartPrice(Double.parseDouble(partPrice));
                    outPart.setPartInStock(Integer.parseInt(partInStock));
                    outPart.setMin(Integer.parseInt(partMin));
                    outPart.setMax(Integer.parseInt(partMax));
                    outPart.setCompanyName(partBool);
                    Inventory.addPart(outPart);
                }
                Parent saveAddPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(saveAddPart);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }

    }

    @FXML
    //public void addPartCancelAct(javafx.event.ActionEvent actionEvent) {
    //    }
    public void addPartCancelAct (javafx.event.ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent addPartCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else {
            System.out.println("Process Canceled. ");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partID = Inventory.getPartIDCount();
        addPartsIDNumberLbl.setText("Part ID: " + partID);

    }



}