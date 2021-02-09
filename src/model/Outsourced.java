package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** This is the the OutSourced class. It contains the Outsourced method, getters and setters. */
public class Outsourced extends Part {

    private final StringProperty companyName;

    /** This is the Outsourced method. Called when Outsource radio button is selected.
     * @param id id value that is getting associated with companyName.
     * @param name name value that is getting associated with companyName.
     * @param price price value that is getting associated with companyName.
     * @param stock stock value that is getting associated with companyName.
     * @param min min value that is getting associated with companyName.
     * @param max max value that is getting associated with companyName.
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
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
/** I ran into another issue here when updating the Part.Java page. I forgot to update the values that are to pass in the super method.
 * I fixed it by updating it to match the inputs that are displayed in the parts.java file.  */