package com.utcn.Presentation.Controllers.Admin;

import com.utcn.Business.DataModels.CompositeProduct;
import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DeliveryService;
import com.utcn.Util.TableColumnAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller for the administrator composite table
 */
public class ControllerCompositeTable implements ActionListener {
    private ControllerAdminTable controllerAdminTable;
    private DeliveryService deliveryService;
    private CompositeProduct product;
    private JTable compositeTable;
    private JTable mainTable;
    private JTextField text;
    private JPanel panel;

    public ControllerCompositeTable(ControllerAdminTable controllerAdminTable, DeliveryService deliveryService,
                                    JPanel panel) {
        this.controllerAdminTable = controllerAdminTable;
        this.deliveryService = deliveryService;
        this.panel = panel;
        this.product = null;
    }

    public void setCompositeTable(JTable compositeTable) {
        this.compositeTable = compositeTable;
        initializeTable();
    }

    public void setMainTable(JTable mainTable) {
        this.mainTable = mainTable;
    }

    public void setText(JTextField text) {
        this.text = text;
    }

    /**
     * Resets the product to null and also the table
     */
    public void resetProduct() {
        this.product = null;
        ((DefaultTableModel)compositeTable.getModel()).setRowCount(0);
    }

    /**
     * Initializes the table
     */
    private void initializeTable() {
        String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        ((DefaultTableModel) compositeTable.getModel()).setColumnIdentifiers(columns);
        TableColumnAdapter.stretchColumns(compositeTable);
    }

    /**
     * Updates the table with the given data
     *
     * @param data  Data to appear in the table
     */
    public void updateTable(List<MenuItem> data) {
        DefaultTableModel tableModel = (DefaultTableModel) compositeTable.getModel();
        tableModel.setRowCount(0);

        for (MenuItem menuItem : data) {
            tableModel.addRow(new String[]{menuItem.getTitle(), menuItem.getRating().toString(),
                    menuItem.getCalories().toString(), menuItem.getProtein().toString(), menuItem.getFat().toString(),
                    menuItem.getSodium().toString(), menuItem.getPrice().toString(),
                    CompositeProduct.decomposeProduct(menuItem)});
        }
        TableColumnAdapter.stretchColumns(compositeTable);
        tableModel.fireTableDataChanged();
    }

    /**
     * Removes the product highlighted in the table
     */
    public void removeProduct() {
        if (product == null) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "A product must be selected with CREATE OR MODIFY", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (compositeTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "No table row selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MenuItem target = product.getProducts().stream().filter(menuItem -> menuItem.getTitle().
                equals(String.valueOf(compositeTable.getValueAt(compositeTable.getSelectedRow(), 0)))).
                findFirst().orElse(null);
        if (target == null) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "UNEXPECTED ERROR", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        product.removeProduct(target);
        updateTable(product.getProducts());
        controllerAdminTable.updateTable((List<MenuItem>) deliveryService.getMenu());
        deliveryService.save();
    }

    /**
     * Adds a product to the composite product's ingredients.
     * The product is highlighted in the table.
     */
    public void addProduct() {
        if (product == null) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "A product must be selected with CREATE OR MODIFY", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (mainTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "No table row selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (deliveryService.searchByName(String.valueOf(mainTable.getValueAt(mainTable.getSelectedRow(), 0))) ==
                null) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "UNEXPECTED ERROR", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        product.addProduct(deliveryService.searchByName(
                String.valueOf(mainTable.getValueAt(mainTable.getSelectedRow(), 0))));
        updateTable(product.getProducts());
        controllerAdminTable.updateTable((List<MenuItem>) deliveryService.getMenu());
        deliveryService.save();
    }

    /**
     * Allows editing on the highlighted composite product
     */
    public void editProduct() {
        if (mainTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "No table row selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (deliveryService.searchByName(String.valueOf(mainTable.getValueAt(mainTable.getSelectedRow(), 0))) ==
                null) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "UNEXPECTED ERROR", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!(deliveryService.searchByName(String.valueOf(mainTable.getValueAt(mainTable.getSelectedRow(), 0)))
                instanceof CompositeProduct)) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Only a CompositeProduct can be modified here", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        product = (CompositeProduct) deliveryService.searchByName(
                String.valueOf(mainTable.getValueAt(mainTable.getSelectedRow(), 0)));
        if (!text.getText().isBlank()) {
            if (deliveryService.searchByName(text.getText()) != null) {
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                        "Name exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            product.setTitle(text.getText());
        }
        deliveryService.save();
        updateTable(product.getProducts());
        controllerAdminTable.updateTable((List<MenuItem>) deliveryService.getMenu());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        product = new CompositeProduct();
        if (text.getText().isBlank()) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Name must not be null", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (deliveryService.searchByName(text.getText()) != null) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Name exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        product.setTitle(text.getText());
        deliveryService.getMenu().add(product);
        deliveryService.save();
        updateTable(product.getProducts());
        controllerAdminTable.updateTable((List<MenuItem>) deliveryService.getMenu());
    }

}
