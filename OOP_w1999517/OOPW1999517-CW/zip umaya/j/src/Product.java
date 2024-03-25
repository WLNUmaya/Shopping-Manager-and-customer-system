
public abstract class Product {
    // Attributes that define a product
    private String productID;
    private String productName;
    private int availableItems;
    private double price;

    // Additional attributes for product details
    private String category;        // The category to which the product belongs
    private String info;            // Additional information about the product

    // Constructor to initialize the product attributes
    public Product(String productID, String productName, int availableItems, double price, String category, String info) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
        this.category = category;
        this.info = info;
    }

    // Abstract method to be implemented by subclasses to determine the specific type of product
    public abstract String getProductType();

    // Getters and setters for accessing and modifying private attributes

    // Getter for retrieving the product ID
    public String getProductID() {
        return productID;
    }

    // Getter for retrieving the product name
    public String getProductName() {
        return productName;
    }

    // Setter for updating the product name
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter for retrieving the available quantity of the product
    public int getAvailableItems() {
        return availableItems;
    }

    // Setter for updating the available quantity of the product
    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    // Getter for retrieving the price of the product
    public double getPrice() {
        return price;
    }

    // Setter for updating the price of the product
    public void setPrice(double price) {
        this.price = price;
    }


    // Getter for retrieving the category of the product
    public String getCategory() {
        return category;
    }

    // Getter for retrieving additional information about the product
    public String getInfo() {
        return info;
    }

    // Method to decrease the availability of the product by one
    public void decreaseAvailability() {
        availableItems--;
    }

}
