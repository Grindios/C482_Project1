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
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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
    private TableView<Part> partsTbl;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInStockCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;


    //Products Table
    @FXML
    private TableView<Product> productsTbl;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInStockCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    private static Part selectedPart;
    private static int selectedPartIndex;
    private static Product selectedProduct;
    private static int selectedProductIndex;

    /** This is the add parts method. It navigates the user to the add part screen.
     * @param actionEvent This parameter loads the new screen. */
    @FXML
    public void addPartsAct(ActionEvent actionEvent){
        try {
            Parent addPartsParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
            Scene addPartsScene = new Scene(addPartsParent);
            Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            addPartsStage.setScene(addPartsScene);
            addPartsStage.show();
        }
        catch (IOException e) {

        }
    }

    /** This is the modify part method. It navigates the user to the modify part screen.
     * @param actionEvent This paramter loads the new screen. */
    @FXML
    public void modifyPartsAct(ActionEvent actionEvent) {
            selectedPart = partsTbl.getSelectionModel().getSelectedItem();
            selectedPartIndex = getParts().indexOf(selectedPart);
          if (selectedPart == null ) {
              Alert nullAlert = new Alert(Alert.AlertType.ERROR);
              nullAlert.setTitle("Part Modification Error");
              nullAlert.setHeaderText("The part is NOT able to be modified!");
              nullAlert.setContentText("There was no part selected!");
              nullAlert.showAndWait();
          }
          else {
                try {
                    Parent modifyPartScreen = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
                    Scene modifyPartScene = new Scene(modifyPartScreen);
                    Stage winModifyPart = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    winModifyPart.setTitle("Modify Part");
                    winModifyPart.setScene(modifyPartScene);
                    winModifyPart.show();
                }
                catch (IOException e) {}
            }
    }

    /** This is the delete part method. It deletes the selected part.*/
    @FXML
    private void partDeleteAct() {
        Part part = partsTbl.getSelectionModel().getSelectedItem();
        if(deletePartVal(part))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Products cannot be deleted.");
            alert.setContentText("Product contains at least one part.");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete this part?");
        alert.setContentText("Press OK to delete the part. \nPress Cancel to cancel the deletion.");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            try {
                Part delPart = partsTbl.getSelectionModel().getSelectedItem();
                removePart(delPart.getId());
            }
            catch (NullPointerException e) {
                Alert nullalert = new Alert(Alert.AlertType.ERROR);
                nullalert.setTitle("Part Deletion Error");
                nullalert.setHeaderText("The part was NOT deleted!");
                nullalert.setContentText("There was no part selected!");
                nullalert.showAndWait();
            }
        }
        else {
            alert.close();
        }

    }

    /** This is the search parts method. It searches for parts via partial name or ID. */
    private ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
    @FXML
    private void SearchPartsAct()  {
        String searchPartString = partsSearchTxt.getText().trim();
        partsSearchTxt.clear();
        filteredPartList.clear();
        if (searchPartString.equals("")) {
            updatePartsTable();
        } else {
            boolean found = false;
            try {
                Part searchPart = Inventory.lookupPart(Integer.parseInt(searchPartString));
                if (searchPart != null) {
                    found = true;
                    ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
                    filteredPartList.add(searchPart);
                    partsTbl.setItems(filteredPartList);
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
                for (Part p : getParts()) {
                    if (p.getName().toLowerCase(Locale.ROOT).contains(searchPartString.toLowerCase(Locale.ROOT))){
                        found = true;

                        filteredPartList.add(p);

                    }
                }
                partsTbl.setItems(filteredPartList);
                partsTbl.refresh();
            }

        }
    }
    /** This is the add product method. It navigates the add product screen.
     * @param actionEvent This parameter loads the add product screen. */
    @FXML
    public void addProductsAct(ActionEvent actionEvent) throws IOException{
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addPartsScene = new Scene(addPartsParent);
        Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartsStage.setScene(addPartsScene);
        addPartsStage.show();
    }

    /** This is the modify product method. It navigates to the modify product screen.
     * @param actionEvent This parameter loads the modify product screen. */
    @FXML
    public void modifyProductsAct(ActionEvent actionEvent){

        selectedProduct = productsTbl.getSelectionModel().getSelectedItem();
        selectedProductIndex = getProducts().indexOf(selectedProduct);
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

    /** This is the product delete method. This deletes a selected product once a validation is met. */
    @FXML
    private void productDeleteAct() {
        Product product = productsTbl.getSelectionModel().getSelectedItem();
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
            alert.setContentText("Are you sure you want to delete " + product.getProductName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                removeProduct(product.getProductID());
                updateProductsTbl();
                System.out.println("Product " + product.getProductName() + " was removed.");
            } else {
                System.out.println("Product " + product.getProductName() + " was removed.");
            }
        }
    }

    /** This is the search product method. This searches the product list via partial name or product ID. */
    private ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
    @FXML
    private void SearchProductsAct() {
        String searchProductsString = productsSearchTxt.getText();
        productsSearchTxt.clear();
        if (searchProductsString.equals("")) {
            updateProductsTbl();
        } else {
            boolean found = false;
            try {
                Product searchProduct = Inventory.lookupProduct(Integer.parseInt(searchProductsString));
                if (searchProduct != null) {
                    found = true;
                    ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
                    filteredProductList.add(searchProduct);
                    productsTbl.setItems(filteredProductList);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found!");
                    alert.setContentText("The search term entered does not match any product ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                for (Product p : getProducts()) {

                    if (p.getProductName().toLowerCase(Locale.ROOT).contains(searchProductsString.toLowerCase(Locale.ROOT))){
                        found = true;


                        filteredProductList.add(p);

                    }
                }
                productsTbl.setItems(filteredProductList);
                productsTbl.refresh();

            }

        }
    }

    /** This is the update parts table method. It updates the part table to the newest values. */
    @FXML
    public void updatePartsTable() { partsTbl.setItems(getParts()); }
    /** This is the update products table method. It updates the product table to the newest values. */
    @FXML
    public void updateProductsTbl() { productsTbl.setItems(getProducts()); }

    /** This is the exit program button. It exits the program completely.*/
    @FXML
    public void exitProgramButton() {
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
    /** This is the refresh method. It refreshes the page (was used during testing to clear the page of data).
     * @param actionEvent This parameter reloads the page. */
    public void RefreshAct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent RefreshAct = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(RefreshAct);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** This is the selected part index getter. It is used to retrieve a part via index.
     * @return It returns a selected part index. */
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }
    /** This is the selected product index method. It is sued to retrieve a product via index.
     * @return It returns a selected product index. */
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }

    /** This is the initialize method. Upon page loading it updates parts table and product table. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updatePartsTable();
        productIdCol.setCellValueFactory(new PropertyValueFactory("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory("productName"));
        productInStockCol.setCellValueFactory(new PropertyValueFactory("productInStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory("productPrice"));
        updateProductsTbl();

    }

}
/** When running the the program and adding a part the parts wouldn't display in the parts table. The issue was found after I had done updates to the parts.java file.
 * I fixed the issue by searching for where it would be referencing the information from and fixed the variable naming convention where is
 * was getting called from in the initialize method. After that was done, the information was was displayed as desired. */

/** Being that I worked a lot with version control with this project. In a future project I would like to add a user name input.
 * Where the user has to put in his/her id and password to modify or add anything. The user ID or input then has a user name associated with it.
 * when changes are made, it must be logged to display who made the changes, and when.*/