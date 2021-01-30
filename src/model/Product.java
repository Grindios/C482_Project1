package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
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
   public void addAssociatedPart(Part part){
       associatedParts.add(part);

   }
   // public void setAssociatedPartsList(ObservableList<Part> associatedParts) {
    //        Product.associatedParts = associatedParts;


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
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    //Price Getters and Setters
    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    //In Stock Getters and Setters
    public int getProductInStock() { return productInStock; }
    public void setProductInStock(int productInStock) { this.productInStock = productInStock; }

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

    public boolean setAssociatedPartsList(ObservableList<Part> parts) {
        return this.associatedParts.setAll(parts);
    }

    //  public static void setAssociatedPartsList(ObservableList<Part> associatedParts) {
 //     Product.associatedParts = associatedParts;
 //      // Product.associatedParts = associatedParts;
 //  }











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
