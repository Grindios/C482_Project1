package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;



public class InHouse extends Part {

    protected final IntegerProperty machineID;

    public InHouse() {
        super();
        machineID = new SimpleIntegerProperty();
    }



    //Machine ID Getters and Setters
    public int getMachineID() { return machineID.get(); }
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
}