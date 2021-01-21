package View_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import static View_controller.MainScreen.productsModifyIndex;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;




public class ModifyProduct implements Initializable {

    private ObservableList<Parts> currentParts = FXCollections.observableArrayList();
    private int productIndex = productsModifyIndex();
    private String exceptionMessage = new String();
    private int productID;

    @FXML
    private Label modifyProductsIDNumberLbl;

    @FXML
    private TextField modifyProductsNametxt;

    @FXML
    private TextField modifyProductsInStocktxt;

    @FXML
    private TextField modifyProductsPricetxt;

    @FXML
    private TextField modifyProductsMintxt;

    @FXML
    private TextField modifyProductsMaxtxt;

    @FXML
    private TextField modifyProductsSearchtxt;

    @FXML
    private TextField modifyProductsBooltxt;

    @FXML
    private Button modifyProductsSaveBtn;

    @FXML
    private TableView<Parts> modProductAddTbl;


    @FXML
    private TableColumn<Parts, Integer> modPartIdAddCol;

    @FXML
    private TableColumn<Parts, String> modPartNameAddCol;

    @FXML
    private TableColumn<Parts, Integer> modPartsInStockAddCol;

    @FXML
    private TableColumn<Parts, Double> modPartPriceAddCol;

    @FXML
    private TableView<Parts> modProductDeleteTbl;

    @FXML
    private TableColumn<Parts, Integer> modPartIdDeleteCol;

    @FXML
    private TableColumn<Parts, String> modPartNameDeleteCol;

    @FXML
    private TableColumn<Parts, Integer> modPartsInStockDeleteCol;

    @FXML
    private TableColumn<Parts, Double> modPartPriceDeleteCol;

    //add
    @FXML
    void AddAct(ActionEvent event) {
        Parts part = modProductAddTbl.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateDeletePartsTableView();
    }
    public void updateDeletePartsTableView() {
        modProductDeleteTbl.setItems(currentParts);
    }

    //Delete
    @FXML
    void DeleteAct(ActionEvent event) {
        Parts part = modProductDeleteTbl.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            currentParts.remove(part);
        }
        else {
            System.out.println("You clicked cancel.");
        }
    }

    @FXML
    private void handleModifyProductSave(ActionEvent event) throws IOException {
        String productName = modifyProductsNametxt.getText();
        String productInv = modifyProductsInStocktxt.getText();
        String productPrice = modifyProductsPricetxt.getText();
        String productMin = modifyProductsMintxt.getText();
        String productMax = modifyProductsMaxtxt.getText();

        try {
            exceptionMessage = Products.getProductValidation(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), currentParts, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            else {
                System.out.println("Product name: " + productName);
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                newProduct.setProductName(productName);
                newProduct.setProductInStock(Integer.parseInt(productInv));
                newProduct.setProductPrice(Double.parseDouble(productPrice));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setProductParts(currentParts);
                Inventory.updateProduct(productIndex, newProduct);

                Parent modifyProductSaveParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}