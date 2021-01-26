package model;


public abstract class Parts {

    protected int partID;
    protected String name;
    protected double price = 0.0;
    protected int inStock;
    protected int min;
    protected int max;

    public Parts(){
        this.partID = partID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Price Getters and Setters
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    //InStock Getters and Setters
    public int getInStock() {
        return inStock;
    }
    public void setInStock(int inStock) {
        this.inStock = inStock;
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
    public  static String getPartValidation (String name, int inStock, double price, int max, int min, String PartError) {

        if(name == null){
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
    // Validate add parts for entry type

    //Validate Empty Field
/*
    public static String GetEmptyFields (String partName, String partInStock, String partPrice, String max, String min, String partDyn, String EmptyError) {
        if (partName.equals("")) {
            EmptyError = EmptyError + "The Part Name field cannot be empty. ";
        }
        if (partInStock.equals("")) {
            EmptyError = EmptyError + "\nThe Part Inventory field cannot be empty. ";
        }
        if (partPrice.equals("")) {
            EmptyError = EmptyError + "\nThe Part Price field cannot be empty. ";
        }
        if (max.equals("")) {
            EmptyError = EmptyError + "\nThe Part Max field cannot be empty. ";
        }
        if (min.equals("")) {
            EmptyError = EmptyError + "\nThe Part Min field cannot be empty. ";
        }
        if (partDyn.equals("")) {
            EmptyError = EmptyError + "\nThe Part MachineID or Company Name field cannot be empty. ";
        }
        return EmptyError;
    }*/

}