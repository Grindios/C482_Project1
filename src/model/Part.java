package model;
/**@author Shelby Benscoter*/
/**This is the Part class. It handles the part methods and the getters and setters for their associated variables. */
public abstract class Part {
    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;
    /** This is the part method. Contains the values that make up the parts
     * @param id id value to create part.
     * @param name name value to create part.
     * @param price price value to create part.
     * @param stock stock value to create part.
     * @param min min value to create part.
     * @param max max value to create part. */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }


    /** This is the part ID getter. Called when part ID value is needed.
     * @return returns part ID. */
    public int getId() {
        return id;
    }
    /** This is the part ID setter. Called when part ID needs to be set.
     * @param id Sets the part ID the incremented amount. */
    public void setId(int id) {
        this.id = id;
    }
    /** This is the part name getter. Called when part name value is needed.
     * @return returns part name. */
    public String getName() {
        return name;
    }
    /** This is the part name setter. Called when the part name needs to be set.
     * @param name Sets the part name of the desired part. */
    public void setName(String name) {
        this.name = name;
    }
    /** This is the part price getter. Called when the the part price is needed.
     * @return Returns the part price value. */
    public double getPrice() {
        return price;
    }
    /** This is the part price setter. Called when the part price is needs to be set.
     * @param price Sets the part price of the desired part. */
    public void setPrice(double price) {
        this.price = price;
    }
    /** This is the part inventory getter. Called when the part inventory number is needed.
     * @return  Returns the inventory amount of the desired part. */
    public int getStock() {
        return stock;
    }
    /** This is the part inventory setter. Called when the part inventory amount needs to be set.
     * @param partInStock  Sets the part inventory amount of the desired part. */
    public void setStock(int partInStock) {
        this.stock = partInStock;
    }
    /**This is the minimum amount getter. Called when the minimum amount of parts is needed.
     * @return Returns the value of the minimum amount. */
    public int getMin() {
        return min;
    }
    /** This is the minimum amount setter. Called when the minimum amount of parts needs to be set.
     * @param min Sets the part inventory minimum to the desired amount within validation parameters. */
    public void setMin(int min) {
        this.min = min;
    }
    /** This is the maximum amount getter. Called when the maximum amount of parts is needed.
     * @return Returns the value of the maximum amount.  */
    public int getMax() {
        return max;
    }
    /** This is the maximum amount setter. Called when maximum amount of parts needs to be set.
     * @param max Sets the part inventory maximum to the desired amount within validation parameters. */
    public void setMax(int max) {
        this.max = max;
    }


}