package model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;


    public static void addProduct (Product allProducts) {
        allProducts.add(product)
    }



}
