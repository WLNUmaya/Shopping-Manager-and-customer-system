import java.util.HashMap;
import java.util.Map;

// Represents a shopping cart that stores items and calculates discounts
public class ShoppingCart {

    // Map to store products and their corresponding quantities in the cart
    private Map<Product, Integer> cartItems;

    // Flag to determine if it is the user's first purchase
    private boolean isFirstPurchase;

    // Constructor to initialize the shopping cart
    public ShoppingCart() {
        // Initialize the cartItems map to store products and their quantities
        this.cartItems = new HashMap<>();

        // Set the flag to true, indicating that this is the user's first purchase
        this.isFirstPurchase = true;
    }

    // Method to add items to the shopping cart
    public void addItem(Product product, int quantity) {
        // Retrieve the current quantity for the given product, or 0 if not present
        int currentQuantity = cartItems.getOrDefault(product, 0);

        // Update the quantity for the product in the cart
        cartItems.put(product, currentQuantity + quantity);
    }

    // Getter to retrieve the items in the shopping cart
    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    // Method to calculate the total price of items in the shopping cart
    public double calculateTotalPrice() {
        double totalPrice = 0;

        // Iterate through the items in the cart and calculate the total price
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            // Add the product's price multiplied by quantity to the total
            totalPrice += product.getPrice() * quantity;
        }

        return totalPrice;
    }

    // Method to calculate the discount for the user's first purchase
    public double calculateFirstPurchaseDiscount(double totalPrice) {
        // 10% discount on the first purchase
        if (isFirstPurchase) {
            // Set the flag to false after the first purchase
            isFirstPurchase = false;

            // Calculate and return the discount amount
            return totalPrice * 0.1;
        }
        // No discount for subsequent purchases
        return 0;
    }

    // Method to calculate the discount for buying at least three items of the same category
    public double calculateThreeItemsDiscount() {
        // 20% discount when the user buys at least three products of the same category

        // Maps to store the count and total price for each product category
        Map<String, Integer> categoryCount = new HashMap<>();
        Map<String, Double> categoryTotalPrice = new HashMap<>();

        // Calculate the count and total price for each product category
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            String category = product.getCategory();

            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + quantity);
            categoryTotalPrice.put(category, categoryTotalPrice.getOrDefault(category, 0.0) + product.getPrice() * quantity);
        }

        double threeItemsDiscount = 0;
        // Iterate through the categories to calculate the discount for each
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            String category = entry.getKey();
            int itemCount = entry.getValue();

            // Calculate the number of sets of three items eligible for discount
            int discountItemCount = itemCount / 3;

            // Calculate the discount amount for the category and add to the total discount
            double categoryDiscount = discountItemCount * categoryTotalPrice.get(category) * 0.2;
            threeItemsDiscount += categoryDiscount;
        }

        return threeItemsDiscount;
    }
}
