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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static View_controller.MainScreen.*;
import static model.Product.*;


public class ModifyProduct implements Initializable {

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
    private TableView<Part> modProductAddTbl;
    @FXML
    private TableColumn<Part, Integer> modPartIdAddCol;
    @FXML
    private TableColumn<Part, String> modPartNameAddCol;
    @FXML
    private TableColumn<Part, Integer> modPartsInStockAddCol;
    @FXML
    private TableColumn<Part, Double> modPartPriceAddCol;
    @FXML
    private TableView<Part> modProductAssocTbl;
    @FXML
    private TableColumn<Part, Integer> modPartIdAssocCol;
    @FXML
    private TableColumn<Part, String> modPartNameAssocCol;
    @FXML
    private TableColumn<Part, Integer> modPartsInStockAssocCol;
    @FXML
    private TableColumn<Part, Double> modPartPriceAssocCol;

    public static ObservableList<Part> currentAssocParts = FXCollections.observableArrayList();
    private int productIndex = getSelectedProductIndex();
    private String catchMessage = new String();
    private int productID;

    //add
    @FXML
    public void AddProductAct(ActionEvent event) {
        Part part = modProductAddTbl.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert nullalert = new Alert(Alert.AlertType.ERROR);
            nullalert.setTitle("Associated Part Addition Error");
            nullalert.setHeaderText("The part was not added!");
            nullalert.setContentText("A part was not selected!");
            nullalert.showAndWait();
        }
        else {
            addAssociatedPart(part);
            modProductAssocTbl.setItems(getAssociatedPartsList());
        }
    }



    @FXML
    public void DeleteAct(javafx.event.ActionEvent event) {
        Part part = modProductAssocTbl.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            currentAssocParts.remove(part);
        } else {
            System.out.println("You clicked cancel.");
        }
    }


    @FXML
    public void SaveModProductAct(javafx.event.ActionEvent event) {
        String name = modifyProductsNametxt.getText();
        String inStock = modifyProductsInStocktxt.getText();
        String price = modifyProductsPricetxt.getText();
        String min = modifyProductsMintxt.getText();
        String max = modifyProductsMaxtxt.getText();

        try {
            catchMessage = Product.getProductValidation(name,
                    Integer.parseInt(inStock),
                    Double.parseDouble(price),
                    Integer.parseInt(max),
                    Integer.parseInt(min),
                    catchMessage);
            if (catchMessage.length()> 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Product must contain at least one part.");
                alert.showAndWait();
            }
            else {
                Product addProduct = new Product();
                addProduct.setProductID(productID);
                addProduct.setName(name);
                addProduct.setInStock(Integer.parseInt(inStock));
                addProduct.setPrice(Double.parseDouble(price));
                addProduct.setMax(Integer.parseInt(max));
                addProduct.setMin(Integer.parseInt(min));
                addProduct.setAssociatedPartsList(currentAssocParts);
                Inventory.updateProduct(getSelectedProductIndex(), addProduct);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage winMainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
                winMainScreen.setTitle("Inventory Management System");
                winMainScreen.setScene(scene);
                winMainScreen.show();
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
    public void updateAssociatedPartsTbl() {
        modProductAssocTbl.setItems(currentAssocParts);
    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product selectedProduct = getSelectedProduct();
        productID = getSelectedProduct().getProductID();
        modifyProductsIDNumberLbl.setText("Auto-Gen: " + productID);
        modifyProductsNametxt.setText(selectedProduct.getName());
        modifyProductsInStocktxt.setText(Integer.toString(selectedProduct.getInStock()));
        modifyProductsPricetxt.setText(Double.toString(selectedProduct.getPrice()));
        modifyProductsMintxt.setText(Integer.toString(selectedProduct.getMin()));
        modifyProductsMaxtxt.setText(Integer.toString(selectedProduct.getMax()));
        currentAssocParts = Product.getAssociatedPartsList();
        modPartIdAddCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modPartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPartsInStockAddCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        modPartPriceAddCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modPartIdAssocCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modPartNameAssocCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modPartsInStockAssocCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        modPartPriceAssocCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateAssociatedPartsTbl();

    }




}