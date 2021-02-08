package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** This is the the OutSourced class. It contains the Outsourced method, getters and setters. */
public class Outsourced extends Part {

    private final StringProperty companyName;

    /** This is the Outsourced method. Called when Outsource radio button is selected. */
    public Outsourced() {
        super();
        companyName = new SimpleStringProperty();
    }

    /** This is the Company Name getter. Called when company name information is needed.
     * @return Returns company name. */
    public String getCompanyName() {
        return companyName.get();
    }
    /** This is the company name setter. Called when the company name needs to be set.
     * @param companyName Sets the value of company name. */
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }


}