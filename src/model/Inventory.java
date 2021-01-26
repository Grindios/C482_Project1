package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Formatter;


public class Inventory {
    private static ObservableList<Products> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Parts> allParts = FXCollections.observableArrayList();
    private static int partIDCount = 0;
    private static int productIDCount = 0;



    //add part and product
    public static void addPart(Parts part) {
        allParts.addAll(part);
    }
    public static void addProduct( Products products) {
        allProducts.add(products);
    }
    //Update Part & Product
    public static void updatePart(int partIndex, Parts part) {
        allParts.set(partIndex, part);
    }
    public static void updateProduct(int productIndex,Products product) {
        allProducts.set(productIndex, product);
    }


    // lookup part & Product
    public static Parts lookupPart (int partID) {
        for(Parts p : allParts) {
            if(p.getPartID() == partID)
                return p;
        }
        return null;
    }
    public static Products lookupProduct(int productID) {
        for ( Products p : allProducts) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }





    //Delete Parts
    public static void deletePart(Parts parts){
        allParts.remove(parts);
    }
    public static boolean deletePartVal(int part) {
        boolean isFound = false;
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getAssociatedPartsList().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }




    //Delete Product
    public static boolean deleteProductVal(Products products) {
        boolean isFound = false;
        int productID = products.getProductID();
        for (int i=0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == productID) {
                if (!allProducts.get(i).getAssociatedPartsList().isEmpty()) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }
    public static void deleteProduct(Products products){
        allProducts.remove(products);
    }




    //Count
    public static int getPartIDCount() {
        partIDCount++;
        return partIDCount;
    }
    public static int getProductIDCount() {
        productIDCount++;
        return productIDCount;
    }




    //Get all Parts & Products
    public static void setAllProducts(ObservableList<Products> allProducts) {
        Inventory.allProducts = allProducts;
    }
    public static void setAllParts(ObservableList<Parts> allParts) {
        Inventory.allParts = allParts;
    }

    public static ObservableList<Parts> getAllParts() {
        return allParts;
    }
    public static ObservableList<Products> getAllProducts() {
        return allProducts;
    }
}