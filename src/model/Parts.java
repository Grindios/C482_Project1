package model;


public abstract class Parts {

    protected int partID;
    protected String partName;
    protected double partPrice = 0.0;
    protected int partInStock;
    protected int min;
    protected int max;
    public Parts(){
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partInStock = partInStock;
        this.min = min;
        this.max = max;
    }



    //PartID Getters & Setters
    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    //PartName Getters & Setters
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    //PartPrice Getters & Setters
    public double getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    //PartInStock Getters & Setters
    public int getPartInStock() {
        return partInStock;
    }

    public void setPartInStock(int partInStock) {
        this.partInStock = partInStock;
    }

    //Min Getters & Setters
    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    //Max Getters & Setters
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }


    // Validate Content Entry

    public  static String getPartValidation (String partName, int partInStock, double partPrice, int max, int min, String PartError) {
        if(partName == null){
            PartError = PartError + "Name Field required. ";
        }
        if (partInStock < 1) {
            PartError = PartError + "\nThe Inventory cannot be less than 1. ";
        }
        if (partPrice <= 0) {
            PartError = PartError + "\nThePrice must be greater than $0. ";
        }
        if (max < min) {
            PartError = PartError + "\nThe Maximum stock must be greater than the Minimum stock. ";
        }
        if (partInStock > max) {
            PartError = PartError + "\nThe Inventory must be less than or equal to the Maximum stock. ";
        }
        if (partInStock < min) {
            PartError = PartError + "\nThe Inventory must be greater than the or equal to the Minimum stock. ";
        }
        return PartError;
    }

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
