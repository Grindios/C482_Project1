package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Products> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Parts> allParts = FXCollections.observableArrayList();
    private static int partIDCount = 0;
    private static int productID = 0;
    private static String partName;
    private static String productName;

    //add part and product
    public static void addPart(Parts part) {
        allParts.addAll(part);
    }

    public static void addProduct(Products product) {
        allProducts.add(product);
    }


    // lookup part
    public static Parts lookupPart(int partID) {
        for(Parts p : allParts) {
            if(p.getPartID() == partID) {
                return p;
            }
        }
        return null;
    }
    public static Parts lookupPart(String partName){
        for(Parts p : allParts) {
            if(p.getPartName() == partName) {
                return p;
            }
        }
        return null;
    }
    // lookup product
    public static Products lookupProduct(int productID) {
        for (Products p : allProducts) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }
    public static Products lookupProduct(String productName){
        for (Products p : allProducts) {
            if(p.getProductName() == productName){
                return p;
            }
        }
        return null;
    }

    //Update Part & Product

    public static void updatePart(int partIndex, Parts part) {
        allParts.set(partIndex, part);
    }
    public static void updateProduct(int productIndex, Products product) {
        allProducts.set(productIndex, product);
    }

    //Delete Parts

    public static void deletePart(Parts parts){
        allParts.remove(parts);
    }
    public static boolean deletePartVal(int partID) {
        for(Parts p: allParts){
            if (p.getPartID() == partID){
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }

    //Delete Product
    public static boolean deleteProduct(int productID) {
        for (Products p: allProducts){
            if (p.getProductID() == productID){
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }

    //Count
    public static int getPartIDCount() {
        partIDCount++;
        return partIDCount;
    }

    //Get all Parts & Products

    public static ObservableList<Parts> getAllParts() {
        return allParts;
    }

    public static ObservableList<Products> getAllProducts() {
        return allProducts;
    }
}
