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

    //Parts Pane


    @FXML
    private TextField partsSearchTxt;


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
    //Products Pane
    @FXML
    private TextField productsSearchTxt;
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

    public static int partsModifyIndex() {

        return modifyPartsIndex;
    }

    public static int productsModifyIndex() {

        return modifyProductsIndex;
    }

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

                //partsIndex = Inventory.lookupPart(searchPart);
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

    // Search part list I am going to move this over
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


    // end of code snippet



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
        modifyParts = partsTbl.getSelectionModel().getSelectedItem();
        modifyPartsIndex = getAllParts().indexOf(modifyParts);
        Parent modifyPartsParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene modifyPartsScene = new Scene(modifyPartsParent);
        Stage modifyPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        modifyPartsStage.setScene(modifyPartsScene);
        modifyPartsStage.show();
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

    //Update Tables
    //parts
    public void updatePartsTableView() {
        partsTbl.setItems(getAllParts());
    }

    //UpdateTables
    //Products
    public void updateProductsTableView() {
        productsTbl.setItems(getAllProducts());
    }
    //Products
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
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);
        Stage modifyProductStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        modifyProductStage.setScene(modifyProductScene);
        modifyProductStage.show();
    }
    //Exit Button
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
    public void RefreshAct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent RefreshAct = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(RefreshAct);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}