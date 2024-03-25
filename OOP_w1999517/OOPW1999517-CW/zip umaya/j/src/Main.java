import java.util.Scanner;

public class Main {

    // Creating instances of WestminsterShoppingManager and Scanner
    private static WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;

    // Main method
    public static void main(String[] args) {
        // Authenticate user and determine user role
        String userAuth = authentication();

        // Based on user role, navigate to admin or user functions
        if (userAuth.equals("admin")) {
            adminRoll();
        } else if (userAuth.equals("user")) {
            userRoll();
        }
    }

    // Method for user authentication
    private static String authentication() {
        // Prompt user for username and password
        System.out.print("Enter username: ");
        String enteredUsername = scanner.next();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.next();

        // Create User object with entered credentials
        User user = new User(enteredUsername, enteredPassword);

        // Check if entered credentials match admin or user credentials
        if (user.getUsername().equals("admin") && user.getPassword().equals("admin123")) {
            currentUser = user;
            return "admin";
        } else if (user.getUsername().equals("user") && user.getPassword().equals("user123")) {
            currentUser = user;
            return "user";
        } else {
            return "invalid";
        }
    }

    // Admin functions
    private static void adminRoll() {
        int choice;

        // Admin menu loop
        do {
            // Display admin options
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of products");
            System.out.println("4. Save product list to file");
            System.out.println("5. Load data from the save file");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = shoppingManager.getScanner().nextInt();

            switch (choice) {
                case 1:
                    // Add a new product
                    System.out.print("Enter product type (1 for Electronics, 2 for Clothing): ");

                    int productTypeChoice = 0;
                    boolean validChoice = false;

                    while (!validChoice) {
                        try {
                            // Get product type choice (Electronics or Clothing)
                            productTypeChoice = scanner.nextInt();
                            scanner.nextLine();

                            // Check if the entered choice is valid
                            if (productTypeChoice == 1 || productTypeChoice == 2) {
                                validChoice = true;
                            } else {
                                System.out.print("Invalid choice. Please enter 1 for Electronics or 2 for Clothing: ");
                            }
                        } catch (java.util.InputMismatchException e) {
                            // Handle non-integer input
                            System.out.print("Invalid input. Please enter 1 for Electronics or 2 for Clothing: ");
                            scanner.nextLine(); // Consume the invalid input
                        }
                    }

                    // Initialize a flag to check if the entered product ID is valid
                    boolean isValidProductID = false;

                    do {
                        // Collect and validate the product ID
                        System.out.print("Enter product ID (e.g., A12345): ");
                        String productID = scanner.nextLine();

                        // Validate the product ID format
                        if (WestminsterShoppingManager.isValidProductID(productID)) {

                            isValidProductID = true;  // Set the flag to true if the product ID is valid


                            System.out.print("Enter product name: ");
                            // Collect and validate the product name (should be a string)
                            String productName = "";
                            boolean validProductName = false;

                            while (!validProductName) {
                                System.out.print("Enter product name: ");
                                productName = scanner.nextLine();

                                // Validate that the product name contains only letters and spaces
                                if (productName.matches("[a-zA-Z\\s]+")) {
                                    validProductName = true;  // Exit the loop if the input is a valid string
                                } else {
                                    System.out.println("Invalid product name. Please use only letters and spaces.");
                                }
                            }

                            System.out.print("Enter available items: ");
                            // Collect and validate the available items (should be an integer)
                            int availableItems = 0;
                            boolean validAvailableItems = false;

                            while (!validAvailableItems) {
                                try {
                                    System.out.print("Enter available items: ");
                                    availableItems = scanner.nextInt();
                                    validAvailableItems = true;  // Exit the loop if the input is a valid integer
                                } catch (java.util.InputMismatchException e) {
                                    // Handle non-integer input
                                    System.out.println("Invalid input. Please enter a valid integer for available items.");
                                    scanner.nextLine();  // Consume the invalid input
                                }
                            }

                            System.out.print("Enter price: ");
                            // Initialize a flag to check if the entered price is valid
                            boolean validPrice = false;
                            double price = 0.0;

                            while (!validPrice) {
                                try {
                                    // Collect and validate the price (should be a double)
                                    System.out.print("Enter price: ");
                                    price = scanner.nextDouble();
                                    scanner.nextLine(); // Consume the newline character

                                    if (price >= 0) {
                                        validPrice = true;  // Exit the loop if the input is a valid double
                                    } else {
                                        System.out.println("Invalid price. Please enter a non-negative number.");
                                    }
                                } catch (java.util.InputMismatchException e) {
                                    // Handle non-double input
                                    System.out.println("Invalid input. Please enter a valid number for the price.");
                                    scanner.nextLine(); // Consume the invalid input
                                }
                            }

                            if (productTypeChoice == 1) {
                                // Electronics
                                System.out.print("Enter brand: ");
                                String brand = scanner.nextLine();

                                System.out.print("Enter warranty period (in months, enter 0 if not applicable): ");

                                int totalWarrantyMonths = 0;
                                boolean validInput = false;
                                while (!validInput) {
                                    try {
                                        System.out.print("Enter the total number of months: ");
                                        totalWarrantyMonths = Integer.parseInt(scanner.nextLine());
                                        validInput = true;  // Exit the loop if the input is a valid integer
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a valid integer for the warranty period in months.");
                                    }
                                }

                                Electronics electronics = new Electronics(productID, productName, availableItems, price, brand, totalWarrantyMonths);
                                shoppingManager.addProduct(electronics);

                            } else if (productTypeChoice == 2) {
                                // Clothing
                                System.out.print("Enter size (XS, S, M, L, XL, XXL): ");
                                // Initialize a flag to check if the entered size is valid
                                boolean validSize = false;
                                String size = "";

                                while (!validSize) {
                                    // Collect and validate the size
                                    System.out.print("Enter size (XS, S, M, L, XL, XXL): ");
                                    size = scanner.nextLine().toUpperCase(); // Convert to uppercase for case-insensitive comparison

                                    // Validate the size format
                                    if (size.matches("^(XS|S|M|L|XL|XXL)$")) {
                                        validSize = true;  // Exit the loop if the input is a valid size
                                    } else {
                                        System.out.println("Invalid size. Please enter one of the following sizes: XS, S, M, L, XL, XXL");
                                    }
                                }

                                System.out.print("Enter color: ");
                                // Collect and validate the color
                                String color = "";
                                boolean validColor = false;

                                while (!validColor) {
                                    System.out.print("Enter color: ");

                                    color = scanner.nextLine();

                                    // Validate that the color contains only letters and spaces
                                    if (color.matches("[A-Za-z ]+")) {
                                        validColor = true;  // Exit the loop if the color is valid
                                    } else {
                                        System.out.println("Invalid color. Please use only letters and spaces.");
                                    }
                                }

                                Clothing clothing = new Clothing(productID, productName, availableItems, price, size, color);
                                shoppingManager.addProduct(clothing);
                                clothing.setAvailableItems(availableItems);
                                clothing.setPrice(price);

                            } else {
                                System.out.println("Invalid choice for product type.");
                            }

                        } else {
                            System.out.println("Invalid product ID format. Please use a capital letter followed by 5 digits.");
                            System.out.print("Do you want to try again? (yes/no): ");
                            String tryAgainChoice = scanner.nextLine();

                            if (!tryAgainChoice.equalsIgnoreCase("yes")) {
                                // User chose not to try again, break out of the loop
                                break;
                            }
                        }
                    } while (!isValidProductID);

                    break;  // Exit the switch statement or handle the invalid input as needed

                case 2:
                    // Delete a product
                    System.out.print("Enter product ID to delete: ");
                    String deleteProductID = scanner.next();
                    shoppingManager.deleteProduct(deleteProductID);
                    break;

                case 3:
                    // Print the list of products
                    shoppingManager.printProductList();
                    break;

                case 4:
                    // Example: Saving products to a file with a default file name
                    String defaultFileName = "productList.txt";
                    shoppingManager.saveToFile(defaultFileName);
                    System.out.println("Products saved to " + defaultFileName);
                    break;

                case 5:
                    // Load product list from file
                    String loadFileName = "productList.txt";
                    shoppingManager.loadFromFile(loadFileName);

                    // Display the loaded product list
                    shoppingManager.printProductList();
                    break;

                case 0:
                    // Exit
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

    // User functions
    private static void userRoll() {
        int choice;

        do {
            System.out.println("1. Manage GUI"); // Added option for managing shopping cart
            System.out.println("2. Login to the admin");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = shoppingManager.getScanner().nextInt();

            switch (choice) {

                case 1:
                    // Open Online Store GUI
                    OnlineStoreGUI.displayGUI(shoppingManager);
                    break;

                case 2:
                    // navigate to the manager
                    String userAuth = authentication();
                    if (userAuth.equals("admin")) {
                        adminRoll();
                    } else {
                        System.out.println("Invalid admin credentials");
                    }
                    break;
                case 0:
                    // Exit
                    System.out.println("Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);

        shoppingManager.scanner.close();
    }
}