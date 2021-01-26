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
import model.Parts;
import model.Products;

import java.io.IOException;
import java.net.URL;
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
    private TableView<Parts> partsTbl;
    @FXML
    private TableColumn<Parts, Integer> partIdCol;
    @FXML
    private TableColumn<Parts, String> partNameCol;
    @FXML
    private TableColumn<Parts, Integer> partsInStockCol;
    @FXML
    private TableColumn<Parts, Double> partPriceCol;


    //Products Table
    @FXML
    private TableView<Products> productsTbl;
    @FXML
    private TableColumn<Products, Integer> productIdCol;
    @FXML
    private TableColumn<Products, String> productNameCol;
    @FXML
    private TableColumn<Products, Integer> productInStockCol;
    @FXML
    private TableColumn<Products, Double> productPriceCol;





    private static Parts modifyParts;
    private static int modifyPartsIndex;
    private static Products modifyProducts;
    private static int modifyProductsIndex;


    public static int partsModifyIndex() { return modifyPartsIndex; }
    public static int productsModifyIndex() { return modifyProductsIndex; }

    //Parts
    @FXML
    private void SearchPartsAct(ActionEvent event) throws IOException {
        String searchPartString = partsSearchTxt.getText();
        if (searchPartString.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("No parts found.");
            alert.setContentText("Search does not match any existing parts.");
            alert.showAndWait();
        } else {
            boolean found = false;
            try {
                Parts searchPart = Inventory.lookupPart(Integer.parseInt(searchPartString));
                if (searchPart != null) {
                    found = true;
                    ObservableList<Parts> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(searchPart);
                    partsTbl.setItems(filteredPartsList);
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
             for (Parts p : getAllParts()) {

                if (p.getName().equals(searchPartString)){
                     found = true;

                     ObservableList<Parts> filteredPartsList = FXCollections.observableArrayList();
                     filteredPartsList.add(p);
                     partsTbl.setItems(filteredPartsList);
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
                Products searchProduct = Inventory.lookupProduct(Integer.parseInt(searchProductsString));
                if (searchProduct != null) {
                    found = true;
                    ObservableList<Products> filteredProductsList = FXCollections.observableArrayList();
                    filteredProductsList.add(searchProduct);
                    productsTbl.setItems(filteredProductsList);
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
                for (Products p : getAllProducts()) {

                    if (p.getName().equals(searchProductsString)){
                        found = true;

                        ObservableList<Products> filteredProductsList = FXCollections.observableArrayList();
                        filteredProductsList.add(p);
                        productsTbl.setItems(filteredProductsList);
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
    @FXML
    public void addPartsAct(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }
    @FXML
    public void modifyPartsAct(ActionEvent actionEvent) throws IOException{
        try {
            modifyParts = partsTbl.getSelectionModel().getSelectedItem();
    if (modifyParts == null ) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Part not selected ");
        alert.setContentText("You must select a part. ");
        alert.showAndWait();
    }

      else{
            modifyPartsIndex = getAllParts().indexOf(modifyParts);
            Parent modifyPartsParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modifyPartsScene = new Scene(modifyPartsParent);
            Stage modifyPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            modifyPartsStage.setScene(modifyPartsScene);
            modifyPartsStage.show();
        }
        }
        catch (IOException e) {

        }
    }
    @FXML
    public void updatePartsTable(){
        partsTbl.setItems(Inventory.getAllParts());
    }
    @FXML
    private void partDeleteAct(ActionEvent event) {
        Parts part = partsTbl.getSelectionModel().getSelectedItem();

        if (deletePartVal(part.getPartID())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Part cannot be deleted!");
            alert.setContentText("Part is being used by one or more products.");
            alert.showAndWait();

        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                deletePart(part);
                updatePartsTable();
                System.out.println("Part " + part.getName() + " was removed.");

            }

            else {
                System.out.println("Part " + part.getName() + " was not removed.");
            }
        }


    }
    public void updatePartsTableView() { partsTbl.setItems(getAllParts()); }


    //Products

    private static Products selectedProduct;
    private static int selectedProductIndex;

    public static Products getSelectedProduct() {
        return selectedProduct;
    }

    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }
    public void updateProductsTableView() { productsTbl.setItems(getAllProducts()); }
    @FXML
    public void addProductsAct(ActionEvent actionEvent) throws IOException{
        Parent addProductsParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductsScene = new Scene(addProductsParent);
        Stage addProductsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addProductsStage.setScene(addProductsScene);
        addProductsStage.show();
    }

    @FXML
    public void modifyProductsAct(ActionEvent actionEvent) throws IOException{
        selectedProduct = productsTbl.getSelectionModel().getSelectedItem();
        selectedProductIndex = getAllProducts().indexOf(selectedProduct);
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
    @FXML
    private void productDeleteAct(ActionEvent event) {
        Products product = productsTbl.getSelectionModel().getSelectedItem();
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
            alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                deleteProduct(product);
                updateProductsTableView();
                System.out.println("Product " + product.getName() + " was removed.");
            } else {
                System.out.println("Product " + product.getName() + " was removed.");
            }
        }
    }


    // new
    private static Parts selectedPart;

    private static int selectedPartIndex;

    public static Parts getSelectedPart() {
        return selectedPart;
    }
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    public static ObservableList<Parts> selectedAssocPart = FXCollections.observableArrayList();

    // new

    //Exit Button & Refresh
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
    public void RefreshAct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent RefreshAct = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(RefreshAct);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //Initializable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInStockCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updatePartsTableView();
        productIdCol.setCellValueFactory(new PropertyValueFactory("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        productInStockCol.setCellValueFactory(new PropertyValueFactory("inStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
        updateProductsTableView();

    }

}