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
import javafx.scene.control.cell.PropertyValueFactory;
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
    private String catchMessage= new String();
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
    public void DeleteAct(javafx.event.ActionEvent event) {
        Parts part = modProductDeleteTbl.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete " + part.getName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            currentParts.remove(part);
        } else {
            System.out.println("You clicked cancel.");
        }
    }

    @FXML
    public void SaveModProductAct(javafx.event.ActionEvent event) {
        String productName = modifyProductsNametxt.getText();
        String productInStock = modifyProductsInStocktxt.getText();
        String productPrice = modifyProductsPricetxt.getText();
        String productMin = modifyProductsMintxt.getText();
        String productMax = modifyProductsMaxtxt.getText();

        try {
            if (currentParts.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Product must contain at least one part.");
                alert.showAndWait();
            }
            else {
                System.out.println("Product name: " + productName);
                Products newProduct = new Products();
                newProduct.setProductID(productID);
                newProduct.setName(productName);
                newProduct.setInStock(Integer.parseInt(productInStock));
                newProduct.setPrice(Double.parseDouble(productPrice));
                newProduct.setMin(Integer.parseInt(productMin));
                newProduct.setMax(Integer.parseInt(productMax));
                newProduct.setAssociatedParts(currentParts);
                Inventory.updateProduct(productIndex, newProduct);

                Parent modifyProductSaveParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }

    // Cancel Button

    public void ModifyProductCancelAct (javafx.event.ActionEvent event) throws IOException {
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
    public void initialize(URL url, ResourceBundle rb) {
        Products product = model.Inventory.getAllProducts().get(productIndex);
        productID = model.Inventory.getAllProducts().get(productIndex).getProductID();
        modifyProductsIDNumberLbl.setText("Auto-Gen: " + productID);
        modifyProductsNametxt.setText(product.getName());
        modifyProductsInStocktxt.setText(Integer.toString(product.getInStock()));
        modifyProductsPricetxt.setText(Double.toString(product.getPrice()));
        modifyProductsMintxt.setText(Integer.toString(product.getMin()));
        modifyProductsMaxtxt.setText(Integer.toString(product.getMax()));
        currentParts = product.getAssociatedParts();
        modPartIdAddCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modPartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPartsInStockAddCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        modPartPriceAddCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modPartIdDeleteCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modPartNameDeleteCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPartsInStockDeleteCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        modPartPriceDeleteCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateAssociatedPartsTV();
        updateDeletePartsTableView();
    }

    public void updateAssociatedPartsTV() {
        modProductAddTbl.setItems(model.Inventory.getAllParts());
    }



}