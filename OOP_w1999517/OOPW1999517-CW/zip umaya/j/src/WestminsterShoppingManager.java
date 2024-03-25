import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class WestminsterShoppingManager implements ShoppingManager {

    // List to store products, current user, and scanner for input
    private ArrayList<Product> productList;
    private User currentUser;
    protected Scanner scanner;

    // Constructor to initialize the shopping manager
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.currentUser = null;
        this.scanner = new Scanner(System.in);
    }

    // Getter for the scanner
    public Scanner getScanner() {
        return scanner;
    }

    // Getter for the product list
    public ArrayList<Product> getProductList() {
        return productList;
    }

    // Method to add a product to the list
    public void addProduct(Product product) {
        String productID = product.getProductID();
        String productType = product.getProductType();

        // Check if a product with the same ID and type already exists
        for (Product existingProduct : productList) {
            if (existingProduct.getProductID().equals(productID) && existingProduct.getProductType().equals(productType)) {
                // If exists, update the existing product
                updateExistingProduct(existingProduct, product);
                return;
            }
        }

        // If no existing product found, add the new product
        if (productList.size() < 50) {
            productList.add(product);
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Cannot add more products. Maximum limit reached.");
        }
    }

    // Method to update an existing product
    private void updateExistingProduct(Product existingProduct, Product newProduct) {
        System.out.println("Product with ID " + existingProduct.getProductID() + " and type " + existingProduct.getProductType() + " already exists. Updating information.");

        // Update common product information
        existingProduct.setProductName(newProduct.getProductName());
        existingProduct.setAvailableItems(newProduct.getAvailableItems());
        existingProduct.setPrice(newProduct.getPrice());

        // Check and update specific information based on product type
        if (newProduct instanceof Electronics && existingProduct instanceof Electronics) {
            updateElectronics((Electronics) existingProduct, (Electronics) newProduct);
        } else if (newProduct instanceof Clothing && existingProduct instanceof Clothing) {
            updateClothing((Clothing) existingProduct, (Clothing) newProduct);
        } else {
            System.out.println("Mismatched product types during update.");
        }

        System.out.println("Product information updated successfully.");
    }

    // Method to update electronics-specific information
    private void updateElectronics(Electronics existingElectronics, Electronics newElectronics) {
        existingElectronics.setBrand(newElectronics.getBrand());
        existingElectronics.setWarrantyPeriod(newElectronics.getWarrantyPeriod());
    }

    // Method to update clothing-specific information
    private void updateClothing(Clothing existingClothing, Clothing newClothing) {
        existingClothing.setSize(newClothing.getSize());
        existingClothing.setColor(newClothing.getColor());
    }

    // Method to delete a product based on its ID
    public void deleteProduct(String productID) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                productList.remove(product);
                System.out.println("Product deleted successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    // Method to print the product list, sorted by product ID
    public void printProductList() {
        // Sort the productList based on Product ID using a lambda comparator.
        Collections.sort(productList, (p1, p2) -> p1.getProductID().compareTo(p2.getProductID()));

        // Printing product information
        for (Product product : productList) {
            System.out.println("Product ID: " + product.getProductID());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Product Type: " + product.getProductType());
            System.out.println("Available Items: " + product.getAvailableItems());
            System.out.println("Price: $" + product.getPrice());

            // Check product type and print specific information
            if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period: " + electronics.getWarrantyPeriod() + " months");
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                System.out.println("Size: " + clothing.getSize());
                System.out.println("Color: " + clothing.getColor());
            }

            System.out.println("---------------------");
        }
    }

    // Method to save the product list to a file
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    // Write electronics information to file
                    writer.println("Electronics," + product.getProductID() + "," + product.getProductName() + "," +
                            product.getAvailableItems() + "," + product.getPrice() + "," +
                            ((Electronics) product).getBrand() + "," + ((Electronics) product).getWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    // Write clothing information to file
                    writer.println("Clothing," + product.getProductID() + "," + product.getProductName() + "," +
                            product.getAvailableItems() + "," + product.getPrice() + "," +
                            ((Clothing) product).getSize() + "," + ((Clothing) product).getColor());
                } else {
                    // Unknown product type encountered
                    System.out.println("Unknown product type: " + product.getClass().getSimpleName());
                }
            }
            System.out.println("Product list saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving product list to file.");
            e.printStackTrace();
        }
    }

    // Method to load product information from a file
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line and create product objects based on category
                String[] split = line.split(",");
                String category = split[0];
                if (category.equals("Electronics")) {
                    // Create and add electronics product
                    Product electronics = new Electronics(
                            split[1], split[2], Integer.parseInt(split[3]), Double.parseDouble(split[4]), split[5], Integer.parseInt(split[6])
                    );
                    addProduct(electronics);
                } else {
                    // Create and add clothing product
                    Product clothing = new Clothing(
                            split[1], split[2], Integer.parseInt(split[3]), Double.parseDouble(split[4]), split[5], split[6]
                    );
                    addProduct(clothing);
                }
            }
            System.out.println("Product list loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error loading product list from file.");
            e.printStackTrace();
        }
    }

    // Method to validate the product ID format
    public static boolean isValidProductID(String productID) {
        // Check if the product ID has the correct format (capital letter followed by 5 digits)
        return productID.matches("[A-Z]\\d{5}");
    }
}
