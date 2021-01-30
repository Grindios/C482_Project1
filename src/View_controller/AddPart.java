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

    @FXML
    public void SaveAddPartsAct(javafx.event.ActionEvent event) throws IOException {
        String name = addPartsNametxt.getText();
        String inStock = addPartsInStocktxt.getText();
        String price = addPartsPricetxt.getText();
        String min = addPartsMintxt.getText();
        String max = addPartsMaxtxt.getText();
        String partBool = addPartsBooltxt.getText();




        try {
            catchError = Part.getPartValidation(name,
                    Integer.parseInt(inStock),
                    Double.parseDouble(price),
                    Integer.parseInt(max),
                    Integer.parseInt(min)
                    , catchError);
            if (catchError.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error adding Part");
                alert.setContentText(catchError);
                alert.showAndWait();
                catchError = "";
            }
            Boolean mID = addPartsInHouseRdBtn.isSelected();
            Boolean outSrc = addPartsOutsourceRdBtn.isSelected();
            if(mID == false && outSrc == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("In House or Outsourced must be Selected ");
                alert.setContentText(catchError);
                alert.showAndWait();
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
                    Inventory.addPart(inPart);
                } else {
                    System.out.println("Part name: " + name);
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setPartName(name);
                    outPart.setPartPrice(Double.parseDouble(price));
                    outPart.setPartInStock(Integer.parseInt(inStock));
                    outPart.setMin(Integer.parseInt(min));
                    outPart.setMax(Integer.parseInt(max));
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
            alert.setContentText("Invalid Entry ");
            alert.showAndWait();

        }

    }



    private String catchError = new String();
    private int partID;

    private boolean isOutsourced;
    @FXML
    public void selectAddPartInHouse(javafx.event.ActionEvent event) {
        isOutsourced = false;
        addPartsBoolLbl.setText("Machine ID");
        addPartsBooltxt.setPromptText("Machine ID");
        addPartsInHouseRdBtn.setSelected(true);
        addPartsOutsourceRdBtn.setSelected(false);
    }

    @FXML
    public void setAddPartsOutsourceRdBtn(javafx.event.ActionEvent event) {
        isOutsourced = true;
        addPartsBoolLbl.setText("Company Name");
        addPartsBooltxt.setPromptText("Company Name");
        addPartsOutsourceRdBtn.setSelected(true);
        addPartsInHouseRdBtn.setSelected(false);
    }
    //Add parts




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partID = Inventory.getPartIDCount();
        addPartsIDNumberLbl.setText("Part ID: " + partID);

    }



}