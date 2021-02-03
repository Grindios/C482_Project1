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
import model.Part;

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

    private boolean isOutsourced;
    int partIndex = View_controller.MainScreen.getSelectedPartIndex();
    private String catchMessage = new String();
    private int partID;

/*
* above are variables
*
*
*
* below are methods
* */




    //Handles the selection of in house radio button
    @FXML
    public void selectModPartInHouse(javafx.event.ActionEvent event) {
        isOutsourced = false;
        modifyPartsBoolLbl.setText("Machine ID");
        modifyPartsBooltxt.setPromptText("Machine ID");
        modifyPartsInHouseRdBtn.setSelected(true);
        modifyPartsOutsourceRdBtn.setSelected(false);
    }

    //Handles the selection of outsourced radio button
    @FXML
    public void setModPartsOutsourceRdBtn(javafx.event.ActionEvent event) {
        isOutsourced = true;
        modifyPartsBoolLbl.setText("Company Name");
        modifyPartsBooltxt.setPromptText("Company Name");
        modifyPartsInHouseRdBtn.setSelected(false);
        modifyPartsOutsourceRdBtn.setSelected(true);
    }

    //Saves the modification of the selected part
    @FXML
    public void SaveModPartsAct(javafx.event.ActionEvent event) throws IOException {
        String name = modifyPartsNametxt.getText();
        String inStock = modifyPartsInStocktxt.getText();
        String price = modifyPartsPricetxt.getText();
        String min = modifyPartsMintxt.getText();
        String max = modifyPartsMaxtxt.getText();
        String partBool = modifyPartsBooltxt.getText();

        try {
            catchMessage = AddPart.getPartValidation(name,
                    Integer.parseInt(inStock),
                    Double.parseDouble(price),
                    Integer.parseInt(max),
                    Integer.parseInt(min),
                    catchMessage);
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
                    System.out.println("Part name: " + name);
                    InHouse inPart = new InHouse();
                    inPart.setPartID(partID);
                    inPart.setPartName(name);
                    inPart.setPartPrice(Double.parseDouble(price));
                    inPart.setPartInStock(Integer.parseInt(inStock));
                    inPart.setMin(Integer.parseInt(min));
                    inPart.setMax(Integer.parseInt(max));
                    inPart.setMachineID(Integer.parseInt(partBool));
                    Inventory.updatePart(partIndex, inPart);
                }
                else {
                    System.out.println("Part name: " + name);
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setPartName(name);
                    outPart.setPartPrice(Double.parseDouble(price));
                    outPart.setPartInStock(Integer.parseInt(inStock));
                    outPart.setMin(Integer.parseInt(min));
                    outPart.setMax(Integer.parseInt(max));
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
            alert.setContentText("Invalid Entry ");
            alert.showAndWait();
        }

    }

    //Cancels the modification of the selected part
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
        Part part = model.Inventory.getParts().get(partIndex);
        partID = model.Inventory.getParts().get(partIndex).getPartID();
        modifyPartsIDNumberLbl.setText("Auto-Gen: " + partID);
        modifyPartsNametxt.setText(part.getPartName());
        modifyPartsInStocktxt.setText(Integer.toString(part.getPartInStock()));
        modifyPartsPricetxt.setText(Double.toString(part.getPartPrice()));
        modifyPartsMintxt.setText(Integer.toString(part.getMin()));
        modifyPartsMaxtxt.setText(Integer.toString(part.getMax()));
        if (part instanceof InHouse) {
            modifyPartsBoolLbl.setText("Machine ID");
            modifyPartsBooltxt.setText(Integer.toString(((InHouse) model.Inventory.getParts().get(partIndex)).getMachineID()));
            modifyPartsInHouseRdBtn.setSelected(true);
        }
        else {
            modifyPartsBoolLbl.setText("Company Name");
            modifyPartsBooltxt.setText((((Outsourced) Inventory.getParts().get(partIndex)).getCompanyName()));
            modifyPartsOutsourceRdBtn.setSelected(true);
        }
    }
}