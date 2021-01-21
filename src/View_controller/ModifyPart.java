package View_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Parts;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class ModifyPart implements Initializable {
    @FXML
    private RadioButton modifyPartsInHouseRdBtn;
    @FXML
    private RadioButton modifyPartsOutsourceRdBtn;
    @FXML
    private Label modifyPartsIDNumberLbl;
    @FXML
    private TextField modifyPartsNametxt;
    @FXML
    private TextField modifyPartsInStocktxt;
    @FXML
    private TextField modifyPartsPricetxt;
    @FXML
    private TextField modifyPartsMintxt;
    @FXML
    private TextField modifyPartsMaxtxt;
    @FXML
    private Label modifyPartsBoolLbl;
    @FXML
    private TextField modifyPartsBooltxt;
    @FXML
    private Button modifyPartsSaveBtn;

    // Buttons?

    private boolean isOutsourced;
    int partIndex = View_controller.MainScreen.partsModifyIndex();
    private String catchMessage = new String();
    private int partID;


    @FXML
    public void selectModPartInHouse(javafx.event.ActionEvent event) {
        isOutsourced = false;
        modifyPartsInHouseRdBtn.setSelected(false);
        modifyPartsBoolLbl.setText("Machine ID");
        modifyPartsBooltxt.setText("");
        modifyPartsBooltxt.setPromptText("Machine ID");

    }

    @FXML
    public void selectModPartOutsourceRdBtn(javafx.event.ActionEvent event) {
        isOutsourced = true;
        modifyPartsOutsourceRdBtn.setSelected(false);
        modifyPartsBoolLbl.setText("Company Name");
        modifyPartsBooltxt.setText("");
        modifyPartsBooltxt.setPromptText("Company Name");

    }
    //Modify parts

    @FXML
    public void SaveModPartsAct(javafx.event.ActionEvent event) throws IOException {
        String partName = modifyPartsNametxt.getText();
        String partInStock = modifyPartsInStocktxt.getText();
        String partPrice = modifyPartsPricetxt.getText();
        String partMin = modifyPartsMintxt.getText();
        String partMax = modifyPartsMaxtxt.getText();
        String partBool = modifyPartsBooltxt.getText();

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
            }
            else {
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
                    Inventory.updatePart(partIndex, inPart);
                }
                else {
                    System.out.println("Part name: " + partName);
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartPrice(Double.parseDouble(partPrice));
                    outPart.setPartInStock(Integer.parseInt(partInStock));
                    outPart.setMin(Integer.parseInt(partMin));
                    outPart.setMax(Integer.parseInt(partMax));
                    outPart.setCompanyName(partBool);
                    Inventory.updatePart(partIndex, outPart);
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
    public void ModifyPartCancelAct (javafx.event.ActionEvent event) throws IOException {
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



        /* Parts part = model.Inventory.getAllParts().get(partIndex);
        partID = model.Inventory.getAllParts().get(partIndex).getPartID();
        modifyPartsIDNumberLbl.setText("Auto-Gen: " + partID);
        modifyPartsNametxt.setText(part.getPartName());
        modifyPartsInStocktxt.setText(Integer.toString(part.getPartInStock()));
        modifyPartsPricetxt.setText(Double.toString(part.getPartPrice()));
        modifyPartsMintxt.setText(Integer.toString(part.getMin()));
        modifyPartsMaxtxt.setText(Integer.toString(part.getMax()));

        if (part instanceof InHouse) {
            modifyPartsBoolLbl.setText("Machine ID");
            modifyPartsBooltxt.setText(Integer.toString(((InHouse) model.Inventory.getAllParts().get(partIndex)).getMachineID()));
            modifyPartsInHouseRdBtn.setSelected(true);
        }*/

    }



}

