package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**This is the Product class. It handles the product methods and the getters and setters for their associated variables. */
public class Product {
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    protected int productID;
    protected String productName;
    protected double productPrice = 0.0;
    protected int productInStock;
    protected int min;
    protected int max;
/** This is the product method. */
    public Product() {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInStock = productInStock;
        this.min = min;
        this.max = max;

    }
   /**This is the product ID getter. Called when the product ID value is needed.
    * @return Returns the product ID. */
    public int getProductID() { return productID; }
    /**This is the product ID setter. Called when the product ID needs to be set.
     * @param productID Value that set the product id to its incremented amount. */
    public void setProductID(int productID) { this.productID = productID; }
    /**This is the product name getter. Called when the product name value is needed.
     * @reutn Returns the product name. */
    public String getProductName() { return productName; }
    /**This is the product name setter. Called when the product name needs to be set.
     * @param productName Value that sets the product name to the users input. */
    public void setProductName(String productName) { this.productName = productName; }
    /**This is the product price getter. Called when the product price value is needed.
     * @return Returns the product price value. */
    public double getProductPrice() { return productPrice; }
    /** This is the product price setter. Called when the product price value needs to be set.
     * @param productPrice Value that sets the product price to the users input. */
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }
    /** This is the product inventory getter. Called when the inventory amount is needed.
     * @return  Returns the product inventory value. */
    public int getProductInStock() { return productInStock; }
    /** This is the product inventory setter. Called when the inventory amount needs to be set.
     * @param productInStock Value that sets the product inventory to the validated amount. */
    public void setProductInStock(int productInStock) { this.productInStock = productInStock; }
    /** This is the product minimum getter. Called when the the product minimum is needed.
     * @return  Returns the minimum value to the associated product. */
    public int getMin() { return min; }
    /** This is the product minimum setter. Called when the product minimum needs to be set.
     * @param min Value that sets the product minimum to the desired amount. */
    public void setMin(int min) { this.min = min; }
    /** This is the product maximum getter. Called when the product maximum is needed.
     * @return Returns the product maximum to the selected product. */
    public int getMax() { return max; }
    /** This is the product maximum setter. Called when the product maximum needs to be set.
     * @param max Value that set the product maximum to the associated product. */
    public void setMax(int max) { this.max = max; }
    /** This is the associated parts list getter. Called when an associated part needs to be recalled from and associated product.
     * @return returns the associated part to the product. */
    public ObservableList<Part> getAssociatedPartsList() {
        return associatedParts;
    }
    /** This is the add associated part method (setter). It adds the selected associated part/s to the selected product.
     * @param part This is the selected part for the product. */
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }
}
