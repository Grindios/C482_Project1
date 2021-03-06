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

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static model.Inventory.getParts;


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

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String catchError = new String();
    private int productID;

  /** This is the search product method. It is called when the user looks for either part ID or part name (partial or full). */
  private ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
    @FXML
    public void SearchProductAct() {
        String searchPartIDString = addProductSearchTxt.getText();
        addProductSearchTxt.clear();
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
                    if (p.getName().toLowerCase(Locale.ROOT).contains(searchPartIDString.toLowerCase(Locale.ROOT))) {
                        found = true;

                        filteredPartsList.add(p);

                    }
                }
                addProductPartsTbl.setItems(filteredPartsList);
                addProductPartsTbl.refresh();

            }

            if (found == false) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Search Warning");
                alert.setHeaderText("There were no parts found!");
                alert.setContentText("The search term entered does not match any part name!");
                alert.showAndWait();
                updatePartsTable();

            }
        }
    }


/** This is the add parts method. It handles the addition of parts to the associated product table. */
    @FXML
    public void AddPartsAct() {
        Part part = addProductPartsTbl.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;
          if (part == null) {
              return;
          } else {
              int id = part.getId();
              for (int i = 0; i < currentParts.size(); i++)
                  if (currentParts.get(i).getId() == id) {
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



    /** This is the delete part method. It removes an associated part from the associated part list. */
    @FXML
    public void DeleteItemAct() {
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

    /** This is the save items method. It saves the newly added product, and associated parts to the newly added product.
     * @param event This parameter is what kicks of the loading of the main screen. */
    @FXML
    public void SaveItemsAct(javafx.event.ActionEvent event) {
        String name = addProductsNameTxt.getText();
        String inStock = addProductsInStockTxt.getText();
        String price = addProductsPriceTxt.getText();
        String max = addProductsMaxTxt.getText();
        String min = addProductsMinTxt.getText();
        try {
            catchError = getProductValidation(name,
                    Integer.parseInt(inStock),
                    Double.parseDouble(price),
                    Integer.parseInt(max),
                    Integer.parseInt(min),
                    catchError);


            catchError = getEmptyFields(name, inStock, price, max, min, catchError);
            if (catchError.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product Addition Warning");
                alert.setHeaderText("The product was NOT added!");
                alert.setContentText(catchError);
                alert.showAndWait();
                catchError = "";
            } else {
                    try {

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

                            for (Part p : currentParts) {
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
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error Adding Product");
                        alert.setContentText("Invalid Entry ");
                        alert.showAndWait();
                    }
                }

        }

            catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Invalid Entry ");
            alert.showAndWait();
        }
    }




    /** This is the cancel method. When clicked the user will be prompted if they are sure they want to cancel.
     * @param event The parameter that calls the main screen. */
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

    /** This is the product validation method. It validates the product values to meet project specification.
     * @param name This is the name value that is validated.
     * @param inStock This is the in stock value that is validated.
     * @param price This is the price value that is validated.
     * @param  max this is the price value that is validated.
     * @param min  this is the price value that is validated.
     * @param ProductError this is the part error that will later be aggregated.
     * @return Returns the product error messages. */
    public static String getProductValidation (String name, int inStock, double price, int max, int min, String ProductError){
        if (name == null) {
            ProductError = ProductError + " Name field required. ";
        }
        if (inStock < 1) {
            ProductError = ProductError + "The Product Name field cannot be empty. ";
        }
        if(price <= 0) {
            ProductError = ProductError + "\nThe Price must be greater than $0; ";
        }
        if (max < min) {
            ProductError = ProductError + "\nThe Maximum stock must be greater than the Minimum stock";
        }
        if (inStock > max) {
            ProductError = ProductError + "\nThe Inventory Must be less than or equal to the Maximum stock. ";
        }
        if (inStock < min) {
            ProductError = ProductError + "\nThe Inventory must be greater than or equal to the Minimum stock. ";
        }
        return ProductError;
    }
    /** This is the empty field validator. This validates the input for any empty entries.
     * @param name This is the name value that is validated.
     * @param inStock This is the in stock value that is validated.
     * @param price This is the price value that is validated.
     * @param min This is the min inventory value that is validated.
     * @param max This is the max inventory value that is validated.
     * @param ProductEmpty This is the part error that will later be aggregated.
     * @return Returns the product error messages. */
    public static String getEmptyFields (String name, String inStock, String price, String max, String min, String ProductEmpty) {
        if (name.equals("")) {
            ProductEmpty = ProductEmpty + "The Product Name field cannot be empty. ";
        }
        if (inStock.equals("")) {
            ProductEmpty = ProductEmpty + "\nThe Product Inventory field cannot be empty. ";
        }
        if (price.equals("")) {
            ProductEmpty = ProductEmpty + "\nThe Product Price field cannot be empty. ";
        }
        if (max.equals("")) {
            ProductEmpty = ProductEmpty + "\nThe Product Max field cannot be empty. ";
        }
        if (min.equals("")) {
            ProductEmpty = ProductEmpty + "\nThe product Min field cannot be empty. ";
        }
        return ProductEmpty;
    }

    /** This is the update parts table method. It updates the table upon loading the page. */
    @FXML
    public void updatePartsTable() { addProductPartsTbl.setItems(getParts()); }
    /** This is the update associated table. It loads the associated parts table upon loading the page. */
    @FXML
    public void updateAssocTable() {addProductAssocTbl.setItems(currentParts);}

    /**This is the initialize method. It updates the parts table and displays the product incremented count. When running the the program and adding a part the parts wouldn't display in the parts table. The issue was found after I had done updates to the parts.java file.
     * I fixed the issue by searching for where it would be referencing the information from and fixed the variable naming convention where is
     * was getting called from in the initialize method. After that was done, the information was was displayed as desired.*/
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        addProductIDAddColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameAddColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInStockAddColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceAddColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        updatePartsTable();
        addProductAssocIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameAssocColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInStockAssocColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceAssocColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateAssocTable();
        productID = Inventory.getProductIDCount();
        addProductsIDNumberLbl.setText("Part ID :" + productID);

    }

}

