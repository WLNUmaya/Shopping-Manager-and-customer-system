public class Clothing extends Product {

    // Instance variables specific to Clothing
    public String size;
    private String color;

    // Constructor to initialize Clothing with specific attributes
    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        // Call the constructor of the superclass (Product) to set common attributes
        super(productID, productName, availableItems, price, "Clothing", "Size: " + size + ", Color: " + color);

        // Initialize Clothing-specific attributes
        this.size = size;
        this.color = color;
    }

    // Getters and setters for Clothing-specific attributes

    // Getter for the size
    public String getSize() {
        return size;
    }

    // Setter for the size
    public void setSize(String size) {
        this.size = size;
    }

    // Getter for the color
    public String getColor() {
        return color;
    }

    // Setter for the color
    public void setColor(String color) {
        this.color = color;
    }

    // Override method to get the product type for Clothing
    @Override
    public String getProductType() {
        return "Clothing";
    }

    // Override method to get a concise information string about Clothing
    @Override
    public String getInfo() {
        return "  " + size + " , " + color;
    }
}
