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

    private String catchError = new String();
    private int partID;
    private boolean isOutsourced;

   /** This is the cancel method. When clicked the user will be prompted if they are sure they want to cancel.
    * @param event The parameter that calls the main screen. */
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

    /** This is the save method. It saves the added part.
     * @param event The parameter that calls the main screen. */
    @FXML
    public void SaveAddPartsAct(javafx.event.ActionEvent event) throws IOException {
        String name = addPartsNametxt.getText();
        String inStock = addPartsInStocktxt.getText();
        String price = addPartsPricetxt.getText();
        String min = addPartsMintxt.getText();
        String max = addPartsMaxtxt.getText();
        String partBool = addPartsBooltxt.getText();
        try {
            catchError = getPartValidation(name,
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
            else {
                Boolean mID = addPartsInHouseRdBtn.isSelected();
                Boolean outSrc = addPartsOutsourceRdBtn.isSelected();
                if(mID == false && outSrc == false){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("In House or Outsourced must be Selected ");
                    alert.setContentText(catchError);
                    alert.showAndWait();
                }
                if (isOutsourced == false) {
                    System.out.println("Part name: " + name);
                    InHouse inPart = new InHouse(partID, name, Double.parseDouble(price), Integer.parseInt(inStock) , Integer.parseInt(min), Integer.parseInt(max));
                    inPart.setId(partID);
                    inPart.setName(name);
                    inPart.setPrice(Double.parseDouble(price));
                    inPart.setStock(Integer.parseInt(inStock));
                    inPart.setMin(Integer.parseInt(min));
                    inPart.setMax(Integer.parseInt(max));
                    inPart.setMachineID(Integer.parseInt(partBool));
                    Inventory.addPart(inPart);
                } else {
                    System.out.println("Part name: " + name);
                    Outsourced outPart = new Outsourced(partID, name, Double.parseDouble(price), Integer.parseInt(inStock) , Integer.parseInt(min), Integer.parseInt(max));
                    outPart.setId(partID);
                    outPart.setName(name);
                    outPart.setPrice(Double.parseDouble(price));
                    outPart.setStock(Integer.parseInt(inStock));
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
/** This is the InHouse radio button method. It sets the boolean value of the isOutsourced variable to false. */
    @FXML
    public void selectAddPartInHouse() {
        isOutsourced = false;
        addPartsBoolLbl.setText("Machine ID");
        addPartsBooltxt.setPromptText("Machine ID");
        addPartsInHouseRdBtn.setSelected(true);
        addPartsOutsourceRdBtn.setSelected(false);
    }

    /** This is the Outsourced radio button method. It sets the boolean value of the isOutsourced variable to true. */
    @FXML
    public void setAddPartsOutsourceRdBtn() {
        isOutsourced = true;
        addPartsBoolLbl.setText("Company Name");
        addPartsBooltxt.setPromptText("Company Name");
        addPartsOutsourceRdBtn.setSelected(true);
        addPartsInHouseRdBtn.setSelected(false);
    }

    /** This is the part validation method. It validates the user input to meet project specifications.
     * @param name This is the name value that is validated.
     * @param inStock This is the in stock value that is validated.
     * @param price This is the price value that is validated.
     * @param  max this is the price value that is validated.
     * @param min  this is the price value that is validated.
     * @param PartError this is the part error that will later be aggregated.
     * @return  Returns the part error messages. */
    public static String getPartValidation(String name, int inStock, double price, int max, int min, String PartError) {

        if (name == null) {
            PartError = PartError + "Name Field required. ";
        }
        if (inStock < 1) {
            PartError = PartError + "\nThe Inventory cannot be less than 1. ";
        }
        if (price <= 0) {
            PartError = PartError + "\nThePrice must be greater than $0. ";
        }
        if (max < min) {
            PartError = PartError + "\nThe Maximum stock must be greater than the Minimum stock. ";
        }
        if (inStock > max) {
            PartError = PartError + "\nThe Inventory must be less than or equal to the Maximum stock. ";
        }
        if (inStock < min) {
            PartError = PartError + "\nThe Inventory must be greater than the or equal to the Minimum stock. ";
        }
        return PartError;
    }



    /** This is the the initialize method. It is called when the screen is loaded.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partID = Inventory.getPartIDCount();
        addPartsIDNumberLbl.setText("Part ID: " + partID);

    }



}