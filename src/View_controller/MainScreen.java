package View_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Parts;
import model.Products;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;

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
    //Products
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

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    }






