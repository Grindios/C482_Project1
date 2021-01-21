package View_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane myMainScreen;

    @FXML
    private Button exitBtn;

    //Parts Pane
    @FXML
    private AnchorPane PartsScreen;

    @FXML
    private Button searchPartsBtn;

    @FXML
    private Button addPartsBtn;

    @FXML
    private Button modifyPartsBtn;

    @FXML
    private Button deletePartsBtn;

    @FXML
    private TextField partsSearchTxt;

    //Product Screen
    @FXML
    private AnchorPane ProductsScreen;

    @FXML
    private Button searchProductsBtn;

    @FXML
    private Button addProductsBtn;

    @FXML
    private Button modifyProductsBtn;

    @FXML
    private Button deleteProductsBtn;

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

    public static int partsModifyIndex(){

        return modifyPartsIndex;
    }
    public static int productsModifyIndex(){

        return modifyProductsIndex;
    }

    //Parts

    @FXML
    public void addPartsAct(ActionEvent actionEvent) throws IOException{
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
            alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                    deletePartVal(part.getPartID());
                    updatePartsTable();
                    System.out.println("Part " + part.getPartName() + " was removed.");

            }

            else {
                System.out.println("Part " + part.getPartName() + " was not removed.");
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
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partsInStockCol.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        updatePartsTableView();
        productIdCol.setCellValueFactory(new PropertyValueFactory("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        productInStockCol.setCellValueFactory(new PropertyValueFactory("inStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory("price"));
        updateProductsTableView();

        }
    }






