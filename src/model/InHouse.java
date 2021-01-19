package model;


public class InHouse extends Part {

    protected String machineID;

    public InHouse() {
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partInStock = partInStock;
        this.min = min;
        this.max = max;
        this.machineID = machineID;
    }

    //Machine ID Getters and Setters

    public String getMachineID() {
        return machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }
}

