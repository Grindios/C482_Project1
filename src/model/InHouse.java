package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


/** This is the InHouse class. It contains the InHouse method, getters and setters. */
public class InHouse extends Part {

    protected final IntegerProperty machineID;

    /**InHouse method. Called when InHouse radio button is selected.
     * @param id id value that is getting associated with machineID.
     * @param name name value that is getting associated with machineID.
     * @param price price value that is getting associated with machineID.
     * @param stock stock value that is getting associated with MachineID.
     * @param min min value that is getting associated with MachineID.
     * @param max max value that is getting associated with MachineID.  */
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
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