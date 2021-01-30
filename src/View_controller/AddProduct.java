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
import static model.Product.*;


public class AddProduct implements Initializable {



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
    private TableView<Part> addProductAssocTbl;
    @FXML
    private TableColumn<Part, Integer> addProductAssocIDColumn;
    @FXML
    private TableColumn<Part, String> addProductNameAssocColumn;
    @FXML
    private TableColumn<Part, Integer> addProductInStockAssocColumn;
    @FXML
    private TableColumn<Part, Double> addProductPriceAssocColumn;
    @FXML
    private TextField addProductSearchTxt;


    @FXML
    public void SearchProductAction() {
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
                    if (p.getPartName().contains(searchPartIDString)) {
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

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();

    @FXML
    public void AddPartsAct(javafx.event.ActionEvent event) {
        Part part = addProductPartsTbl.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;
          if (part == null) {
              return;
          } else {
              int id = part.getPartID();
              for (int i = 0; i < currentParts.size(); i++)
                  if (currentParts.get(i).getPartID() == id) {
                      Alert alert = new Alert(Alert.AlertType.INFORMATION);
                      alert.setTitle("Error");
                      alert.setHeaderText("That Part is already associated.");
                      alert.showAndWait();
                      repeatedItem = true;
                  }
          }
          if (!repeatedItem) {
              currentParts.add(part);
          }
        addProductAssocTbl.setItems(currentParts);





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
                Part part = addProductAssocTbl.getSelectionModel().getSelectedItem();
                currentParts.remove(part);
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


        catchError = Product.getEmptyFields(name, inStock, price, max, min, catchError);
        if (catchError.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product Addition Warning");
            alert.setHeaderText("The product was NOT added!");
            alert.setContentText(catchError);
            alert.showAndWait();
            catchError = "";
        } else {
            if (currentParts.isEmpty()) {
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

                        Product SaveProduct = new Product();
                        SaveProduct.setProductID(productID);
                        SaveProduct.setProductName(name);
                        SaveProduct.setProductInStock(Integer.parseInt(inStock));
                        SaveProduct.setProductPrice(Double.parseDouble(price));
                        SaveProduct.setMax(Integer.parseInt(max));
                        SaveProduct.setMin(Integer.parseInt(min));
                        //SaveProduct.setAssociatedPartsList(currentParts);

                        for(Part p : currentParts)
                        {
                            SaveProduct.addAssociatedPart(p);
                        }
                        Inventory.addProduct(SaveProduct);

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


    @FXML
    public void updatePartsTable() { addProductPartsTbl.setItems(getParts()); }
    @FXML
    public void updateAssocTable() {addProductAssocTbl.setItems(currentParts);}

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        addProductIDAddColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductNameAddColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addProductInStockAddColumn.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        addProductPriceAddColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        updatePartsTable();
        addProductAssocIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductNameAssocColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addProductInStockAssocColumn.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        addProductPriceAssocColumn.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        updateAssocTable();
        productID = Inventory.getProductIDCount();
        addProductsIDNumberLbl.setText("Part ID :" + productID);

    }




}

