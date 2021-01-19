package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.*;

public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;
    private static String partName;
    private static String productName;

    //add part and product
    public static void addPart(Part part) {
        allParts.addAll(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }


    // lookup part
    public static Part lookupPart(int partID) {
        for(Part p : allParts) {
            if(p.getPartID() == partID) {
                return p;
            }
        }
        return null;
    }
    public static Part lookupPart(String partName){
        for(Part p : allParts) {
            if(p.getPartName() == partName) {
                return p;
            }
        }
        return null;
    }
    // lookup product
    public static Product lookupProduct(int productID) {
        for (Product p : allProducts) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }
    public static Product lookupProduct(String productName){
        for (Product p : allProducts) {
            if(p.getProductName() == productName){
                return p;
            }
        }
        return null;
    }

    //Update Part & Product

    public static void updatePart(int partIndex, Part part) {
        allParts.set(partIndex, part);
    }
    public static void updateProduct(int productIndex, Product product) {
        allProducts.set(productIndex, product);
    }

    //Delete Part & Product


    public static boolean deletePart(int partID) {
        for(Part p: allParts){
            if (p.getPartID() == partID){
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }
    public static boolean deleteProduct(int productID) {
        for (Product p: allProducts){
            if (p.getProductID() == productID){
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }

    //Get all Parts & Products

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
