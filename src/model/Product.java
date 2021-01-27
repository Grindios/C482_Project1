package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    protected int productID;
    protected String name;
    protected double price = 0.0;
    protected int inStock;
    protected int min;
    protected int max;

    public Product() {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;

    }
    public static void addAssociatedPart(Part part){
        associatedParts.add(part);
    }


    public static boolean removeAssociatedPart(int partID) {
        for (Part p : associatedParts) {
            if (p.getPartID() == partID) {
                associatedParts.remove(p);
                return true;
            }
        }
        return false;
    }
    public static Part lookupAssociatedPart(int partID) {
        return associatedParts.get(partID);
    }

    //Product ID Getters and Setters
    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }

    //Name Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    //Price Getters and Setters
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    //In Stock Getters and Setters
    public int getInStock() { return inStock; }
    public void setInStock(int inStock) { this.inStock = inStock; }

    //Min Getters and Setters
    public int getMin() { return min; }
    public void setMin(int min) { this.min = min; }

    //Max Getters and Setters
    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }


    //Assoc Getters & Setters
    public static ObservableList<Part> getAssociatedPartsList() {
        return associatedParts;
    }
    public static void setAssociatedPartsList(ObservableList<Part> associatedParts) { Product.associatedParts = associatedParts; }











   //Product Validation

    public static String getProductValidation (String name, int inStock, double price, int max, int min, String ProductError){
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

}
