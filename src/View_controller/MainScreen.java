package View_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;


public class MainScreen implements Initializable {

    //Main Screen
    @FXML
    private Button exitBtn;


    //Search Buttons
    @FXML
    private TextField partsSearchTxt;
    @FXML
    private TextField productsSearchTxt;


    //Parts Table
    @FXML
    private TableView<Part> partsTbl;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInStockCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;


    //Products Table
    @FXML
    private TableView<Product> productsTbl;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInStockCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    private static Part selectedPart;
    private static int selectedPartIndex;
    private static Product selectedProduct;
    private static int selectedProductIndex;

    /*
    * above are variables
    *
    *
    *
    * below are methods
    * */





 //Parts pane methods
//Takes the user to the add parts screen
    @FXML
    public void addPartsAct(ActionEvent actionEvent){
        try {
            Parent addPartsParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
            Scene addPartsScene = new Scene(addPartsParent);
            Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            addPartsStage.setScene(addPartsScene);
            addPartsStage.show();
        }
        catch (IOException e) {

        }
    }

    // takes the user to the selected modify parts screen
    @FXML
    public void modifyPartsAct(ActionEvent actionEvent) {
            selectedPart = partsTbl.getSelectionModel().getSelectedItem();
            selectedPartIndex = getParts().indexOf(selectedPart);
          if (selectedPart == null ) {
              Alert nullAlert = new Alert(Alert.AlertType.ERROR);
              nullAlert.setTitle("Part Modification Error");
              nullAlert.setHeaderText("The part is NOT able to be modified!");
              nullAlert.setContentText("There was no part selected!");
              nullAlert.showAndWait();
          }
          else {
                try {
                    Parent modifyPartScreen = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
                    Scene modifyPartScene = new Scene(modifyPartScreen);
                    Stage winModifyPart = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    winModifyPart.setTitle("Modify Part");
                    winModifyPart.setScene(modifyPartScene);
                    winModifyPart.show();
                }
                catch (IOException e) {}
            }
    }

    //deletes the selected part
    @FXML
    private void partDeleteAct() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete this part?");
        alert.setContentText("Press OK to delete the part. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = partsTbl.getSelectionModel().getSelectedItem();
                removePart(part.getPartID());
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Part Deletion Error");
                nullalert.setHeaderText("The part was NOT deleted!");
                nullalert.setContentText("There was no part selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }

    }

    //searches the parts table for string similarities and ID#
    @FXML
    private void SearchPartsAct(ActionEvent event) throws IOException {
        String searchPartString = partsSearchTxt.getText().trim();
        if (searchPartString.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("No parts found.");
            alert.setContentText("Search does not match any existing parts.");
            alert.showAndWait();
        } else {
            boolean found = false;
            try {
                Part searchPart = Inventory.lookupPart(Integer.parseInt(searchPartString));
                if (searchPart != null) {
                    found = true;
                    ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
                    filteredPartList.add(searchPart);
                    partsTbl.setItems(filteredPartList);
                }

                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                for (Part p : getParts()) {

                    if (p.getPartName().toLowerCase(Locale.ROOT).contains(searchPartString.toLowerCase(Locale.ROOT))){
                        found = true;

                        ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
                        filteredPartList.add(p);
                        partsTbl.setItems(filteredPartList);
                    }
                }
            }
            if (found == false) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Search Warning");
                alert.setHeaderText("There were no parts found!");
                alert.setContentText("The search term entered does not match any part name!");
                alert.showAndWait();
            }
        }
    }



    /*
    *Below are product pane methods
    *
    *
    *
    * */


    //Takes the user to the add products screen
    @FXML
    public void addProductsAct(ActionEvent actionEvent) throws IOException{
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }

    //takes the user to modify the selected product
    @FXML
    public void modifyProductsAct(ActionEvent actionEvent) throws IOException{

        selectedProduct = productsTbl.getSelectionModel().getSelectedItem();
        selectedProductIndex = getProducts().indexOf(selectedProduct);
        if (selectedProduct == null) {
            Alert nullalert = new Alert(Alert.AlertType.ERROR);
            nullalert.setTitle("Product Modification Error");
            nullalert.setHeaderText("The product is NOT able to be modified!");
            nullalert.setContentText("There was no product selected!");
            nullalert.showAndWait();
        }
        else {
            try {
                Parent modifyProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
                Scene modifyProductScene = new Scene(modifyProductParent);
                Stage modifyProductStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                modifyProductStage.setScene(modifyProductScene);
                modifyProductStage.show();
            }
            catch (IOException e) {}
        }
    }

    //takes the user to delete a selected product
    @FXML
    private void productDeleteAct(ActionEvent event) {
        Product product = productsTbl.getSelectionModel().getSelectedItem();
        if(deleteProductVal(product))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Products cannot be deleted.");
            alert.setContentText("Product contains at least one part.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Deletion");
            alert.setHeaderText("Confirm Delete?");
            alert.setContentText("Are you sure you want to delete " + product.getProductName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                removeProduct(product.getProductID());
                updateProductsTbl();
                System.out.println("Product " + product.getProductName() + " was removed.");
            } else {
                System.out.println("Product " + product.getProductName() + " was removed.");
            }
        }
    }

    // Searches the product table for string similarities and ID#
    @FXML
    private void SearchProductsAct(ActionEvent event) throws IOException {
        String searchProductsString = productsSearchTxt.getText();
        if (searchProductsString.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("No parts found.");
            alert.setContentText("Search does not match any existing parts.");
            alert.showAndWait();
        } else {
            boolean found = false;
            try {
                Product searchProduct = Inventory.lookupProduct(Integer.parseInt(searchProductsString));
                if (searchProduct != null) {
                    found = true;
                    ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
                    filteredProductList.add(searchProduct);
                    productsTbl.setItems(filteredProductList);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                for (Product p : getProducts()) {

                    if (p.getProductName().toLowerCase(Locale.ROOT).contains(searchProductsString.toLowerCase(Locale.ROOT))){
                        found = true;

                        ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
                        filteredProductList.add(p);
                        productsTbl.setItems(filteredProductList);
                    }
                }
            }
            if (found == false) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Search Warning");
                alert.setHeaderText("There were no parts found!");
                alert.setContentText("The search term entered does not match any part name!");
                alert.showAndWait();
            }

        }
    }

    //updates the parts table
    @FXML
    public void updatePartsTable() { partsTbl.setItems(getParts()); }
    //updates the product table
    @FXML
    public void updateProductsTbl() { productsTbl.setItems(getProducts()); }

    //exits the program
    @FXML
    public void exitProgramButton(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) exitBtn.getScene().getWindow();
            stage.close();
        }
        else {
            System.out.println("Canceled.");
        }
    }
    //Refresh the program to clear search fields

    public void RefreshAct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent RefreshAct = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(RefreshAct);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //Getters
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }

    //Initializable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partsInStockCol.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        updatePartsTable();
        productIdCol.setCellValueFactory(new PropertyValueFactory("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory("productName"));
        productInStockCol.setCellValueFactory(new PropertyValueFactory("productInStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory("productPrice"));
        updateProductsTbl();

    }

}