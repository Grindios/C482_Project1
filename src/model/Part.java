package model;


public abstract class Part {

    protected int partID;
    protected String partName;
    protected double partPrice = 0.0;
    protected int partInStock;
    protected int min;
    protected int max;

    public Part() {
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partInStock = partInStock;
        this.min = min;
        this.max = max;

    }

    //Part ID Getters and Setters
    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    //Name Getters and Setters
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    //Price Getters and Setters
    public double getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    //InStock Getters and Setters
    public int getPartInStock() {
        return partInStock;
    }

    public void setPartInStock(int partInStock) {
        this.partInStock = partInStock;
    }

    //Min Getters and Setters
    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    //Max Getters and Setters
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    // Validate Content Entry
    public static String getPartValidation(String name, int inStock, double price, int max, int min, String PartError) {

        if (name == null) {
            PartError = PartError + "Name Field required. ";
        }
        if (inStock < 1) {
            PartError = PartError + "\nThe Inventory cannot be less than 1. ";
        }
        if (price <= 0) {
            PartError = PartError + "\nThePrice must be greater than $0. ";
        }
        if (max < min) {
            PartError = PartError + "\nThe Maximum stock must be greater than the Minimum stock. ";
        }
        if (inStock > max) {
            PartError = PartError + "\nThe Inventory must be less than or equal to the Maximum stock. ";
        }
        if (inStock < min) {
            PartError = PartError + "\nThe Inventory must be greater than the or equal to the Minimum stock. ";
        }
        return PartError;
    }
}