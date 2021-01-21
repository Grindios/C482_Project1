package model;



public class Outsourced extends Parts {

    protected String companyName;

    public Outsourced(){
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partInStock = partInStock;
        this.min = min;
        this.max = max;
        this.companyName = companyName;
    }

    // Company Name Getters & Setters

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}