package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Inventory class. It contains the methods that search, remove or add items. */
public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;


    /** This is the add product method. It Handles the add product actions.
     * @param products The product information being added. */
    public static void addProduct( Product products) {
        allProducts.add(products);
    }
    /** This is the remove product method. It Handles the remove product actions.
     * @param productID the selected product ID that is used to delete/remove the product
     * @return returns boolean value.
     */
    public static boolean removeProduct(int productID){
        for (Product p : allProducts){
            if (p.getProductID() == productID) {
                allProducts.remove(p);
                return true;
            }
        }
        return false;
    }

    /** This is the lookup product method. ItHandles the search actions.
     *  @param productID looks up product via product ID.
     *  @return returns p which contains the found product ID or null. */
    public static Product lookupProduct(int productID) {
        for ( Product p : allProducts) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }

    /** This is the update product method. It is called when the product is updated.
     * @param product the modification of the product.
     * @param productIndex the index of the product that was selected to modify.  */
    public static void updateProduct(int productIndex, Product product) {
        allProducts.set(productIndex, product);
    }

    /** This is the add part method. It handles the add part action.
     * @param part The part information being added. */
    public static void addPart(Part part) {
        allParts.addAll(part);
    }
    /** This is the remove part method. It handles the removal of parts action.
     * @param partID The selected partID that is used to delete/remove the part.
     * @return Returns Boolean value. */
    public static boolean removePart(int partID){
        for (Part p : allParts) {
            if (p.getId() == partID) {
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }

    /** This is the lookup part method. It handles the search part action.
     * @param partID Looks up the part via part ID.
     * @return returns the found part or null */
    public static Part lookupPart (int partID) {
        for(Part p : allParts) {
            if(p.getId() == partID)
                return p;
        }
        return null;
    }

    /** This is the update part method. It is called when the part is updated.
     * @param part the modification of the part.
     * @param partIndex the index of the part that was selected to modify. */
    public static void updatePart(int partIndex, Part part) {
        allParts.set(partIndex, part);
    }


    /** This is the Part ID count getter. It handles the increment of Part ID enumeration.
     * @return returns the incremented part ID. */
    public static int getPartIDCount() {
        partID++;
        return partID;
    }
    /** This is the Product ID count getter. It handles the increment of Product ID enumeration.
     * @return Returns the incremented product ID. */
    public static int getProductIDCount() {
        productID++;
        return productID;
    }

    /** This is the Parts ObservableList method. It is called when parts and their associated information needs to be called.
     * @return Returns inventoried parts.  */
    public static ObservableList<Part> getParts() {
        return allParts;
    }
    /** This is the Products ObservableList method. It is called when products and their associated information needs to be called.
     * @return Returns inventoried Products. */
    public static ObservableList<Product> getProducts() {
        return allProducts;
    }


    /** This is the Part Deletion Validation. It is called when a part is going to deleted, it must pass this associated item validation.
     * @param part This is the selected part that is going through validation.
     * @return Returns a boolean value to be handled in the method that called it. */
    public static boolean deletePartVal(Part part) {
        boolean isFound = false;
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getAssociatedPartsList().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }

    /** This is the Product Deletion validation method. It is called when a Product is going to be deleted, it must pass this associated item validation.
     * @param product This is the selected product that is going through validation.
     * @return Returns a boolean value to be handled in the method that called it. */
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


