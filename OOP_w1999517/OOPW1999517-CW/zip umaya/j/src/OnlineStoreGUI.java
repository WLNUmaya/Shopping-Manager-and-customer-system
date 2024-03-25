
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.TableRowSorter;
import java.util.Comparator;
import java.util.stream.Collectors;

// The class represents the graphical user interface (GUI) of an online store application.
public class OnlineStoreGUI extends JFrame {

    // Declare GUI components as class fields.
    private JComboBox<String> categoryComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton viewCartButton;
    private List<Product> productList;
    private DefaultTableModel productTableModel;
    private List<Product> filteredProductList;

    // Create an instance of the ShoppingCart class to manage the shopping cart functionality.
    private ShoppingCart shoppingCart = new ShoppingCart();

    // Constructor method for initializing the GUI.
    public OnlineStoreGUI(ShoppingManager shoppingManager) {
        initializeUI();
        setupEventHandlers();
        loadProductData(shoppingManager);
    }

    // Method to set up the initial appearance of the GUI.
    private void initializeUI() {
        // Set window title, size, and default close operation.
        setTitle("Westminster Shopping Center");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize GUI components such as JComboBox, JTable, JTextArea, and JButtons.
        categoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productTableModel = createProductTableModel();
        productTable = new JTable(productTableModel);
        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton("Add to Shopping Cart");
        viewCartButton = new JButton("View Shopping Cart");

        // Set up sorting for the JTable based on the first column (Product ID).
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(productTableModel);
        productTable.setRowSorter(sorter);
        sorter.setComparator(0, Comparator.naturalOrder());

        // Add components to different regions of the BorderLayout.
        add(createTopPanel(), BorderLayout.NORTH);

        JPanel bottomPanel = createBottomPanel();
        bottomPanel.add(addToCartButton);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JPanel topPanel = createTopPanel();
        topPanel.add(viewCartButton, BorderLayout.EAST);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        centerPanel.add(createDetailsPanel(), BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
    }


    // Method to set up event handlers for GUI components.
    private void setupEventHandlers() {
        // Attach actions to JComboBox for category selection.
        categoryComboBox.addActionListener(e -> updateProductTable((String) categoryComboBox.getSelectedItem()));

        // Attach action to "Add to Shopping Cart" button.
        addToCartButton.addActionListener(e -> addToCart());

        // Attach action to "View Shopping Cart" button.
        viewCartButton.addActionListener(e -> viewShoppingCart());

        // Attach mouse click listener for JTable rows to load product details.
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadProductDetails();
            }
        });
    }


    // Method to load product data from a file using a ShoppingManager.
    private void loadProductData(ShoppingManager shoppingManager) {
        String defaultFileName = "productList.txt";
        shoppingManager.loadFromFile(defaultFileName);
        productList = shoppingManager.getProductList();
        filteredProductList = productList; // Initial load shows all products
        refreshProductTable(filteredProductList);
    }

    // Method to create the DefaultTableModel for the product JTable.
    private DefaultTableModel createProductTableModel() {
        return new DefaultTableModel(new Object[]{"Product ID", "Name", "Category", "Price", "Info"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    // Method to create the top panel of the GUI, containing the category selection JComboBox.
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(new JLabel("Select Product Category:"));
        centerPanel.add(categoryComboBox);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        return topPanel;
    }

    // Method to create the bottom panel of the GUI, initially empty.
    private JPanel createBottomPanel() {
        return new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    // Method to create the details panel at the bottom of the GUI, displaying selected product information.
    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout());

        JLabel detailsLabel = new JLabel("Selected Product: Details");
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 16));

        productDetailsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(productDetailsTextArea);
        // Set the preferred width of the JScrollPane
        scrollPane.setPreferredSize(new Dimension(400, 200)); // Adjust the width as needed

        JPanel innerDetailsPanel = new JPanel(new BorderLayout());
        innerDetailsPanel.add(detailsLabel, BorderLayout.NORTH);
        innerDetailsPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the "Add to Shopping Cart" button directly to innerDetailsPanel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addToCartButton.setPreferredSize(new Dimension(200, 30)); // Adjust the size as needed
        buttonPanel.add(addToCartButton);

        innerDetailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        detailsPanel.add(innerDetailsPanel, BorderLayout.CENTER);

        return detailsPanel;
    }

    // Method to refresh the product JTable based on a given list of products.
    public void refreshProductTable(List<Product> productList) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (Product product : productList) {
            Object[] rowData = new Object[]{product.getProductID(), product.getProductName(), product.getCategory(), product.getPrice(), product.getInfo()};
            model.addRow(rowData);

            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1 && selectedRow == model.getRowCount() - 1) {
                displayProductDetails(product);
            }

            if (product.getAvailableItems() < 3) {
                for (int i = 0; i < model.getColumnCount(); i++) {
                    model.setValueAt("<html><font color='red'>" + model.getValueAt(model.getRowCount() - 1, i) + "</font></html>", model.getRowCount() - 1, i);
                }
            }
        }
    }

    // Method to load product details when a row in the product JTable is clicked.
    private void loadProductDetails() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = getProductById(productId);
            if (selectedProduct != null) {
                displayProductDetails(selectedProduct);
                System.out.println(selectedProduct.getInfo());
            }
        }
    }

    // Method to display detailed information about a selected product in the JTextArea.
    private void displayProductDetails(Product product) {
        String details = "Product ID: " + product.getProductID() + "\n"
                + "Category: " + product.getCategory() + "\n"
                + "Name: " + product.getProductName() + "\n"
                + getProductDetails(product) + "\n"  // Use a helper method for details
                + "Items Available: " + product.getAvailableItems();
        productDetailsTextArea.setText(details);
    }

    // Method to retrieve additional details specific to the product type (Clothing or Electronics).
    private String getProductDetails(Product product) {
        if ("Clothing".equals(product.getCategory())) {
            return "Size: " + ((Clothing) product).getSize() + "\nColor: " + ((Clothing) product).getColor();
        } else if ("Electronics".equals(product.getCategory())) {
            return "Brand: " + ((Electronics) product).getBrand() + "\nWarranty Period: " + ((Electronics) product).getWarrantyPeriod();
        }
        // Handle other categories if needed
        return "";
    }

    // Method to update the product JTable based on the selected product category.
    private void updateProductTable(String selectedCategory) {
        if ("All".equals(selectedCategory)) {
            filteredProductList = productList;
        } else {
            filteredProductList = productList.stream()
                    .filter(product -> selectedCategory.equals(product.getCategory()))
                    .collect(Collectors.toList());
        }
        refreshProductTable(filteredProductList);
    }

    // Method to add a selected product to the shopping cart.
    private void addToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = (String) productTable.getValueAt(selectedRow, 0);
            Product selectedProduct = getProductById(productId);

            if (selectedProduct != null && selectedProduct.getAvailableItems() > 0) {
                addProductToCart(selectedProduct);
                refreshProductTable(filteredProductList);
            }
        }
    }

    // Method to add a selected product to the shopping cart and update the product JTable.
    private void addProductToCart(Product product) {
        shoppingCart.addItem(product, 1);
        product.decreaseAvailability();
    }

    // Method to retrieve a product based on its ID from the product list.
    private Product getProductById(String productId) {
        return productList.stream()
                .filter(product -> productId.equals(product.getProductID()))
                .findFirst()
                .orElse(null);
    }

    // Method to open a new window displaying the contents of the shopping cart.
    private void viewShoppingCart() {
        double totalPrice = shoppingCart.calculateTotalPrice();
        double firstPurchaseDiscount = shoppingCart.calculateFirstPurchaseDiscount(totalPrice);
        double threeItemsDiscount = shoppingCart.calculateThreeItemsDiscount();
        double finalTotalPrice = totalPrice - firstPurchaseDiscount - threeItemsDiscount;

        ShoppingCartFrame shoppingCartFrame = new ShoppingCartFrame(shoppingCart, totalPrice, firstPurchaseDiscount, threeItemsDiscount, finalTotalPrice);
        shoppingCartFrame.setVisible(true);
    }

    // Static method to display the GUI using the provided ShoppingManager instance.
    public static void displayGUI(ShoppingManager shoppingManager) {
        SwingUtilities.invokeLater(() -> {
            new OnlineStoreGUI(shoppingManager).setVisible(true);
        });
    }
}
