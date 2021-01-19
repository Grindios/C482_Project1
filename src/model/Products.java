package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Products {
    public static ObservableList<Parts> associatedParts = FXCollections.observableArrayList();
    protected int productID;
    protected String productName;
    protected double productPrice = 0.0;
    protected int productInStock;
    protected int min;
    protected int max;

    public Products() {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInStock = productInStock;
        this.min = min;
        this.max = max;

    }

    public static void addAssociatedPart(Parts part){
        associatedParts.add(part);
    }

    public static boolean deleteAssociatedPart(int partID) {
        for(Parts p : associatedParts) {
            if(p.getPartID() == partID) {
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }
    public static Parts searchAssocPart(int partID) {
        return associatedParts.get(partID);
    }


    //ProductID Getters & Setters
    public int getProductID() {

        return productID;
    }

    public void setProductID(int productID) {

        this.productID = productID;
    }

    //ProductName Getters & Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {

        this.productName = productName;
    }

    //ProductPrice Getters & Setters
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {

        this.productPrice = productPrice;
    }

    //ProductInStock Getters & Setters
    public int getProductInStock() {

        return productInStock;
    }

    public void setProductInStock(int productInStock) {

        this.productInStock = productInStock;
    }

    //Min Getters & Setters
    public int getMin() {

        return min;
    }

    public void setMin(int min) {

        this.min = min;
    }

    //Max Getters & Setters
    public int getMax() {

        return max;
    }

    public void setMax(int max) {

        this.max = max;
    }

    //Assoc Getters & Setters
    public static ObservableList<Parts> getAllAssociatedParts() {
        return associatedParts;
    }

    public static void setAssocParts(ObservableList<Parts> assocParts) {
        Products.associatedParts = assocParts;
    }

    public static String getProductValidation (int productInStock, double productPrice, int max, int min, String ProductError){
        if (productInStock < 1) {
            ProductError = ProductError + "The Product Name field cannot be empty. ";
        }
        if(productPrice <= 0) {
            ProductError = ProductError + "\nThe Price must be greater than $0; ";
        }
        if (max < min) {
            ProductError = ProductError + "\nThe Maximum stock must be greater than the Minimum stock";
        }
        if (productInStock > max) {
            ProductError = ProductError + "\nThe Inventory Must be less than or equal to the Maximum stock. ";
        }
        if (productInStock < min) {
            ProductError = ProductError + "\nThe Inventory must be greater than or equal to the Minimum stock. ";
        }
        return ProductError;
        }

        public static String getEmptyFields (String productName, String productInStock, String productPrice, String max, String min, String ProductEmpty) {
            if (productName.equals("")) {
                ProductEmpty = ProductEmpty + "The Product Name field cannot be empty. ";
            }
            if (productInStock.equals("")) {
                ProductEmpty = ProductEmpty + "\nThe Product Inventory field cannot be empty. ";
            }
            if (productPrice.equals("")) {
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

        }


