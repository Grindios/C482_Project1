package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Outsourced extends Parts {

    private final StringProperty companyName;

    public Outsourced() {
        super();
        companyName = new SimpleStringProperty();
    }

    //Company Name Getter & Setters
    public String getCompanyName() {
        return companyName.get();
    }
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }


}