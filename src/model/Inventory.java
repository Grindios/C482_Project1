package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;


//add, remove, lookup and update product
    public static void addProduct( Product products) {
        allProducts.add(products);
    }
    public static boolean removeProduct(int productID){
        for (Product p : allProducts){
            if (p.getProductID() == productID) {
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }
    public static Product lookupProduct(int productID) {
        for ( Product p : allProducts) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }
    public static void updateProduct(int productIndex, Product product) {
        allProducts.set(productIndex, product);
    }

    //add, remove, lookup and update part
    public static void addPart(Part part) {
        allParts.addAll(part);
    }
    public static boolean removePart(int partID){
        for (Part p : allParts) {
            if (p.getPartID() == partID) {
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }
    public static Part lookupPart (int partID) {
        for(Part p : allParts) {
            if(p.getPartID() == partID)
                return p;
        }
        return null;
    }
    public static void updatePart(int partIndex, Part part) {
        allParts.set(partIndex, part);
    }


    //Count
    public static int getPartIDCount() {
        partID++;
        return partID;
    }
    public static int getProductIDCount() {
        productID++;
        return productID;
    }

    //getters
    public static ObservableList<Part> getParts() {
        return allParts;
    }
    public static ObservableList<Product> getProducts() {
        return allProducts;
    }




    // Validation

    public static boolean deletePartVal(int part) {
        boolean isFound = false;
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getAssociatedPartsList().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }

    public static boolean deleteProductVal(Product product) {
        boolean isFound = false;
        int productID = product.getProductID();
        for (int i=0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == productID) {
                if (!allProducts.get(i).getAssociatedPartsList().isEmpty()) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }
}


