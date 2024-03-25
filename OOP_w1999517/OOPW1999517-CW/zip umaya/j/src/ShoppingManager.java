
import java.util.*;

// Interface for managing shopping-related operations
public interface ShoppingManager {

    // Method to add a new product to the manager's product list
    void addProduct(Product product);

    // Method to delete a product based on its unique identifier
    void deleteProduct(String productID);

    // Method to print the details of all products in the manager's product list
    void printProductList();

    // Method to save the current product list to a file
    void saveToFile(String filename);

    // Method to load a product list from a file into the manager's product list
    void loadFromFile(String filename);

    // Method to retrieve the product list managed by the shopping manager
    List<Product> getProductList();
}
