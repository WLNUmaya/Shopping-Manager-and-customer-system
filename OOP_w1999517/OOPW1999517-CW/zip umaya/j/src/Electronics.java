public class Electronics extends Product {

    // Instance variables specific to Electronics
    private String brand;
    private int warrantyPeriod;

    // Constructor to initialize Electronics with specific attributes
    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        // Call the constructor of the superclass (Product) to set common attributes
        super(productID, productName, availableItems, price, "Electronics", "Brand: " + brand + ", Warranty Period: " + warrantyPeriod + " months");

        // Initialize Electronics-specific attributes
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and setters for Electronics-specific attributes

    // Getter for the brand
    public String getBrand() {
        return brand;
    }

    // Setter for the brand
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Getter for the warranty period
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // Setter for the warranty period
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // Override method to get the product type for Electronics
    public String getProductType() {
        return "Electronics";
    }

    // Override method to get a concise information string about Electronics
    @Override
    public String getInfo() {
        return " " + brand + "  " + warrantyPeriod + "  ";
    }
}
