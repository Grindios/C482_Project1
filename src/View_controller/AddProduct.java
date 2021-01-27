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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.getParts;
import static model.Product.addAssociatedPart;
import static model.Product.removeAssociatedPart;


public class AddProduct implements Initializable {


    // private ObservableList<Parts> currentParts = FXCollections.observableArrayList();
    @FXML
    private Label addProductsIDNumberLbl;
    @FXML
    private TextField addProductsNameTxt;
    @FXML
    private TextField addProductsInStockTxt;
    @FXML
    private TextField addProductsPriceTxt;
    @FXML
    private TextField addProductsMinTxt;
    @FXML
    private TextField addProductsMaxTxt;
    //Tables
    @FXML
    private TableView<Part> addProductPartsTbl;
    @FXML
    private TableColumn<Part, Integer> addProductIDAddColumn;
    @FXML
    private TableColumn<Part, String> addProductNameAddColumn;
    @FXML
    private TableColumn<Part, Integer> addProductInStockAddColumn;
    @FXML
    private TableColumn<Part, Double> addProductPriceAddColumn;
    @FXML
    private TableView<Part> addProductDeleteTbl;
    @FXML
    private TableColumn<Part, Integer> addProductDeleteIDColumn;
    @FXML
    private TableColumn<Part, String> addProductDeleteNameColumn;
    @FXML
    private TableColumn<Part, Integer> addProductDeleteInStockColumn;
    @FXML
    private TableColumn<Part, Double> addProductDeletePriceColumn;
    @FXML
    private TextField addProductSearchTxt;

    @FXML
    public void SearchProductAction(ActionEvent event) {
        String searchPartIDString = addProductSearchTxt.getText();
        if (searchPartIDString.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Search Warning");
            alert.setHeaderText("There were no parts found!");
            alert.setContentText("You did not enter a part to search for!");
            alert.showAndWait();
        } else {
            boolean found = false;
            try {
                Part searchPart = Inventory.lookupPart(Integer.parseInt(searchPartIDString));
                if (searchPart != null) {
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(searchPart);
                    addProductPartsTbl.setItems(filteredPartsList);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part ID!");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                for (Part p : getParts()) {
                    if (p.getPartName().equals(searchPartIDString)) {
                        found = true;
                        ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                        filteredPartsList.add(p);
                        addProductPartsTbl.setItems(filteredPartsList);
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
    }


    @FXML
    public void AddPartsAct(javafx.event.ActionEvent event) {

        Part part = addProductPartsTbl.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Associated Part Addition Error");
            nullAlert.setHeaderText("The part was not added!");
            nullAlert.setContentText("A part was not selected!");
            nullAlert.showAndWait();
        } else {
            addAssociatedPart(part);
            addProductDeleteTbl.setItems(Product.getAssociatedPartsList());
        }
    }

    @FXML
    public void DeleteItemAct(javafx.event.ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Associated Part");
        alert.setHeaderText("Are you sure you want to delete the associated part?");
        alert.setContentText("Press OK to delete the associated part. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Part part = addProductDeleteTbl.getSelectionModel().getSelectedItem();
                removeAssociatedPart(part.getPartID());
            } catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Associated Part Deletion Error");
                nullalert.setHeaderText("The part was not deleted!");
                nullalert.setContentText("A part was not selected!");
                nullalert.showAndWait();
            }
        } else {
            alert.close();
        }
    }

    @FXML
    public void SaveItemsAct(javafx.event.ActionEvent event) throws IOException {
        String name = addProductsNameTxt.getText();
        String inStock = addProductsInStockTxt.getText();
        String price = addProductsPriceTxt.getText();
        String max = addProductsMaxTxt.getText();
        String min = addProductsMinTxt.getText();
        ObservableList<Part> associatedParts = addProductDeleteTbl.getItems();

        catchError = Product.getEmptyFields(name, inStock, price, max, min, catchError);
        if (catchError.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Addition Warning");
            alert.setHeaderText("The product was NOT added!");
            alert.setContentText(catchError);
            alert.showAndWait();
            catchError = "";
        } else {
            if (associatedParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product Save Error");
                alert.setHeaderText("The product was not saved!");
                alert.setContentText("An associated part was not selected!");
                alert.showAndWait();
            } else {
                try {
                    catchError = Product.getProductValidation(name,
                            Integer.parseInt(inStock),
                            Double.parseDouble(price),
                            Integer.parseInt(max),
                            Integer.parseInt(min),
                            catchError);
                    if (catchError.length() > 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Product Addition Warning");
                        alert.setHeaderText("The product was NOT added!");
                        alert.setContentText(catchError);
                        alert.showAndWait();
                        catchError = "";
                    } else {
                        Product addProduct = new Product();
                        addProduct.setProductID(productID);
                        addProduct.setName(name);
                        addProduct.setInStock(Integer.parseInt(inStock));
                        addProduct.setPrice(Double.parseDouble(price));
                        addProduct.setMax(Integer.parseInt(max));
                        addProduct.setMin(Integer.parseInt(min));
                        addProduct.setAssociatedPartsList(associatedParts);
                        Inventory.addProduct(addProduct);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage winMainScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        winMainScreen.setTitle("Inventory Management System");
                        winMainScreen.setScene(scene);
                        winMainScreen.show();
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    @FXML
    public void CancelAct(javafx.event.ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to the Main screen. \nPress Cancel to stay on this screen.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage winMainScreen = (Stage) ((Node) event.getSource()).getScene().getWindow();
                winMainScreen.setTitle("Inventory Management System");
                winMainScreen.setScene(scene);
                winMainScreen.show();
            } else {
                alert.close();
            }
        } catch (IOException e) {
        }
    }


    //Everything above is vetted


    private String catchError = new String();
    private int productID;




    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        addProductIDAddColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductNameAddColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInStockAddColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductPriceAddColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductDeleteIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductDeleteNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductDeleteInStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductDeletePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productID = Inventory.getProductIDCount();
        addProductsIDNumberLbl.setText("Part ID :" + productID);

    }


}

