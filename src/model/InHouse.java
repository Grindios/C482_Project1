package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


/** This is the InHouse class. It contains the InHouse method, getters and setters. */
public class InHouse extends Part {

    protected final IntegerProperty machineID;

    /**InHouse method. Called when InHouse radio button is selected. */
    public InHouse() {
        super();
        machineID = new SimpleIntegerProperty();
    }
    /** This is the machine ID Getter. Called when Machine ID information is needed.
     * @return Returns machine ID. */
    public int getMachineID() { return machineID.get(); }
    /** Machine ID Setter. Called when Machine ID needs to be set.
     * @param machineID Sets the value of Machine ID. */
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
}