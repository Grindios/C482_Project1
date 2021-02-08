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

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static View_controller.MainScreen.*;
import static model.Inventory.getParts;
import static model.Inventory.getProducts;


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
    private TableView<Part> modPartsAddTbl;
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
    @FXML
    private Button searchBtn;
    @FXML
    private TextField modProductSearchTxt;

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private int productIndex = getSelectedProductIndex();
    private String catchMessage = new String();
    private int productID;



    private ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
    /** This is the search part method. It search parts via partial name or ID number. */
    @FXML
    public void SearchProductPartAction() {
        String searchPartIDString = modProductSearchTxt.getText();
        modProductSearchTxt.clear();
        filteredPartsList.clear();
        if (searchPartIDString.equals("")) {
            updatePartsTable();
        } else {
            boolean found = false;
            try {
                Part searchPart = Inventory.lookupPart(Integer.parseInt(searchPartIDString));
                if (searchPart != null) {
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(searchPart);
                    modPartsAddTbl.setItems(filteredPartsList);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part ID!");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                for (Part p : getParts()) {
                    if (p.getName().toLowerCase(Locale.ROOT).contains(searchPartIDString.toLowerCase(Locale.ROOT))) {
                        found = true;
                        filteredPartsList.add(p);
                    }
                }
                modPartsAddTbl.setItems(filteredPartsList);
                modPartsAddTbl.refresh();
            }
        }
    }

    /** This is the add product method. It adds an associated part to the associated parts table and updates the table.*/
    @FXML
    public void AddProductAct() {
        Part part = modPartsAddTbl.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Associated Part Addition Error");
            nullAlert.setHeaderText("The part was not added!");
            nullAlert.setContentText("A part was not selected!");
            nullAlert.showAndWait();
        }
        else {
            currentParts.add(part);
            updateAssociatedPartsTbl();
        }
    }

    /** This is the delete method. It handles the deletion of an association between a part and a product. */
    @FXML
    public void DeleteAct() {
        Part part = modProductAssocTbl.getSelectionModel().getSelectedItem();
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

    /** This is a save product method. It saves the modifications done to the product and the association of a part to product.
     * @param event This parameter loads the main page. */
    @FXML
    public void SaveModProductAct(javafx.event.ActionEvent event) {
        String name = modifyProductsNametxt.getText();
        String inStock = modifyProductsInStocktxt.getText();
        String price = modifyProductsPricetxt.getText();
        String min = modifyProductsMintxt.getText();
        String max = modifyProductsMaxtxt.getText();

        try {
            catchMessage = AddProduct.getProductValidation(name,
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
                addProduct.setProductName(name);
                addProduct.setProductInStock(Integer.parseInt(inStock));
                addProduct.setProductPrice(Double.parseDouble(price));
                addProduct.setMax(Integer.parseInt(max));
                addProduct.setMin(Integer.parseInt(min));
                for ( int i = 0; i < currentParts.size(); i ++) {
                    addProduct.addAssociatedPart(currentParts.get(i));
                }
                Inventory.updateProduct(productIndex, addProduct);

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

    /** This is the cancel method. This method cancels the modification of a product and returns to main page.
     * @param event This parameter loads the main page. */
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
    /** This is the update associated parts table method. It updates the table to the most current values. */
    public void updateAssociatedPartsTbl() {
        modProductAssocTbl.setItems(currentParts);
    }
    /** This is the update parts table method. This updates the parts table to the most recent associated values. */
    @FXML
    public void updatePartsTable() { modPartsAddTbl.setItems(getParts()); }

    /** This is the initialize method. It loads the information of the product being modified and the the tables being updated. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product selectedProduct = getProducts().get(productIndex);
        productID = getProducts().get(productIndex).getProductID();
        modifyProductsIDNumberLbl.setText("Auto-Gen: " + productID);
        modifyProductsNametxt.setText(selectedProduct.getProductName());
        modifyProductsInStocktxt.setText(Integer.toString(selectedProduct.getProductInStock()));
        modifyProductsPricetxt.setText(Double.toString(selectedProduct.getProductPrice()));
        modifyProductsMintxt.setText(Integer.toString(selectedProduct.getMin()));
        modifyProductsMaxtxt.setText(Integer.toString(selectedProduct.getMax()));
        currentParts.addAll(selectedProduct.getAssociatedPartsList());
        modPartIdAddCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modPartNameAddCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modPartsInStockAddCol.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        modPartPriceAddCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        modPartIdAssocCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modPartNameAssocCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modPartsInStockAssocCol.setCellValueFactory(new PropertyValueFactory<>("partInStock"));
        modPartPriceAssocCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        updateAssociatedPartsTbl();
        updatePartsTable();

    }
}