package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    public static ObservableList<Part> assocParts = FXCollections.observableArrayList();
    protected int productID;
    protected String productName;
    protected double productPrice = 0.0;
    protected int productInStock;
    protected int min;
    protected int max;

    public Product() {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInStock = productInStock;
        this.min = min;
        this.max = max;

    }
    public static void addAssocPart(Part part){
        assocParts.add(part);
    }

    public static boolean removeAssocPart(int partID) {
        for(Part p : assocParts) {
            if(p.getPartID() == partID) {
                assocParts.remove(p);
                return true;
            }
        }
        return false;
    }
    public static Part searchAssocPart(int partID) {
        return assocParts.get(partID);
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
    public static ObservableList<Part> getAssocParts() {
        return assocParts;
    }

    public static void setAssocParts(ObservableList<Part> assocParts) {
        Product.assocParts = assocParts;
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


