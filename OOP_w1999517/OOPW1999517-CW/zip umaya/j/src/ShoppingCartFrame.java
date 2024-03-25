import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// JFrame subclass for displaying the shopping cart details
public class ShoppingCartFrame extends JFrame {

    // Constructor to initialize the frame with shopping cart details and discounts
    public ShoppingCartFrame(ShoppingCart shoppingCart, double totalPrice, double firstPurchaseDiscount, double threeItemsDiscount, double finalTotalPrice) {
        // Set the title and dimensions of the frame
        setTitle("Shopping Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a table model with columns for product details
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Product", "Quantity", "Price"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the cells non-editable
            }
        };

        // Create a JTable to display the shopping cart contents
        JTable cartTable = new JTable(tableModel);
        cartTable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        cartTable.getColumnModel().getColumn(0).setCellRenderer(new ProductCellRenderer());

        // Create a JScrollPane to add scrolling functionality to the table
        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.NORTH);

        // Populate the table with shopping cart details
        for (Product product : shoppingCart.getCartItems().keySet()) {
            int quantity = shoppingCart.getCartItems().get(product);
            tableModel.addRow(new Object[]{product, quantity, String.format("$%.2f", product.getPrice())});
        }

        // Display discounts in a JTextArea
        JTextArea totalPriceTextArea = createDiscountTextArea("Total Price", totalPrice);
        JTextArea firstPurchaseDiscountTextArea = createDiscountTextArea("First Purchase Discount", -firstPurchaseDiscount);
        JTextArea threeItemsDiscountTextArea = createDiscountTextArea("Three Items Discount", -threeItemsDiscount);
        JTextArea finalTotalPriceTextArea = createDiscountTextArea("Final Total Price", finalTotalPrice);

        // Create a JPanel to hold and display the discount JTextAreas
        JPanel discountsPanel = new JPanel(new GridLayout(4, 1));
        discountsPanel.add(totalPriceTextArea);
        discountsPanel.add(firstPurchaseDiscountTextArea);
        discountsPanel.add(threeItemsDiscountTextArea);
        discountsPanel.add(finalTotalPriceTextArea);

        // Add the discounts panel to the frame's SOUTH position
        add(discountsPanel, BorderLayout.SOUTH);
    }

    // Helper method to create and configure a discount text area
    private JTextArea createDiscountTextArea(String label, double value) {
        JTextArea textArea = new JTextArea(label + ": $" + String.format("%.2f", value));
        textArea.setEditable(false);
        return textArea;
    }

    // Inner class for custom cell rendering of the Product column in the table
    private class ProductCellRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Create a JPanel to hold the JLabel with product information
            JPanel panel = new JPanel(new BorderLayout());
            Product product = (Product) value;

            // Generate product information based on its type
            String productInfo;
            if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                productInfo = " ," + product.getProductID() + ",  " + product.getProductName() + ",  " + clothing.getSize() + ", " + clothing.getColor();
            } else if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                productInfo = " " + product.getProductID() + ",  " + product.getProductName() + ", " + electronics.getBrand() + ", " + electronics.getWarrantyPeriod();
            } else {
                // Default case for other types of products
                productInfo = " " + product.getProductID() + ",  " + product.getProductName();
            }

            // Create a JLabel with product information and add it to the panel
            JLabel label = new JLabel(productInfo);
            panel.add(label, BorderLayout.CENTER);
            return panel;
        }
    }
}
