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
import model.Parts;
import model.Products;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import static model.Products.addAssociatedPart;
import static model.Products.getAssociatedPartsList;


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
    private TableView<Parts> addProductPartsTbl;
    @FXML
    private TableColumn<Parts, Integer> addProductIDAddColumn;
    @FXML
    private TableColumn<Parts, String> addProductNameAddColumn;
    @FXML
    private TableColumn<Parts, Integer> addProductInStockAddColumn;
    @FXML
    private TableColumn<Parts, Double> addProductPriceAddColumn;
    @FXML
    private TableView<Parts> addProductDeleteTbl;
    @FXML
    private TableColumn<Parts, Integer> addProductDeleteIDColumn;
    @FXML
    private TableColumn<Parts, String> addProductDeleteNameColumn;
    @FXML
    private TableColumn<Parts, Integer> addProductDeleteInStockColumn;
    @FXML
    private TableColumn<Parts, Double> addProductDeletePriceColumn;

    private ObservableList<Parts> currentParts = FXCollections.observableArrayList();
    private String catchError = new String();
    private int productID;


    //Delete button action
    @FXML
    public void DeleteItemAct(javafx.event.ActionEvent event) {
        Parts parts = addProductDeleteTbl.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Associated Part");
        alert.setHeaderText("Are you sure you want to delete the associated part?");
        alert.setContentText("Press OK to delete the associated part. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                System.out.println("Part deleted.");
                currentParts.remove(parts);
            } catch (NullPointerException e) {
                Alert nullAlert = new Alert(Alert.AlertType.ERROR);
                nullAlert.setTitle("Associated Part Deletion Error");
                nullAlert.setHeaderText("The part was not deleted!");
                nullAlert.setContentText("A part was not selected!");
                nullAlert.showAndWait();

            }
        } else {
            alert.close();
        }
    }

    //Add button action
    @FXML
    public void AddPartsAct(javafx.event.ActionEvent event) {

        Parts part = addProductPartsTbl.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Associated Part Addition Error");
            nullAlert.setHeaderText("The part was not added!");
            nullAlert.setContentText("A part was not selected!");
            nullAlert.showAndWait();
        } else {
            addAssociatedPart(part);
            addProductDeleteTbl.setItems(model.Products.getAssociatedPartsList());
        }
        //   boolean repeatedItem = false;
        //   if (addPart == null) {
        //       return;
        //   } else {
        //       int id = addPart.getPartID();
        //       for (int i = 0; i < currentParts.size(); i++)
        //           if (currentParts.get(i).getPartID() == id) {
        //               Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //               alert.setTitle("Error");
        //               alert.setHeaderText("That Part is already associated.");
        //               alert.showAndWait();
        //               repeatedItem = true;
        //           }
        //   }
        //   if (!repeatedItem) {
        //       currentParts.add(addPart);
        //   }
        //   addProductDeleteTbl.setItems(currentParts);
    }

    //Save button action
    @FXML
    public void SaveItemsAct(javafx.event.ActionEvent event) {
        String name = addProductsNameTxt.getText();
        String inStock = addProductsInStockTxt.getText();
        String price = addProductsPriceTxt.getText();
        String max = addProductsMaxTxt.getText();
        String min = addProductsMinTxt.getText();
        ObservableList<Parts> associatedParts = addProductPartsTbl.getItems();


        if (associatedParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Save Error");
            alert.setHeaderText("The product was not saved!");
            alert.setContentText("An associated part was not selected!");
            alert.showAndWait();
        } else {
            try {
                catchError = Parts.getPartValidation(name,
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
                    Products addProduct = new Products();
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




    @FXML
    public void CancelAct (javafx.event.ActionEvent event) throws IOException {
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit to Main Screen");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to the Main screen. \nPress Cancel to stay on this screen.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(root);
                Stage winMainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
                winMainScreen.setTitle("Inventory Management System");
                winMainScreen.setScene(scene);
                winMainScreen.show();
            }
            else {
                alert.close();
            }
        }
        catch (IOException e) {}
    }
    public void updateAddPartTableView() {
        addProductPartsTbl.setItems(getAllParts());
    }
    public void updateAddProductDeleteTbl() {
        addProductDeleteTbl.setItems(currentParts);
    }
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        addProductIDAddColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductNameAddColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInStockAddColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductPriceAddColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateAddPartTableView();
        addProductDeleteIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductDeleteNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductDeleteInStockColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductDeletePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productID = Inventory.getProductIDCount();
        addProductsIDNumberLbl.setText("Part ID :" + productID);

    }


}

