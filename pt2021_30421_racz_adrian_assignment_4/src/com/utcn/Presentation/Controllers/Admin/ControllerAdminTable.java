package com.utcn.Presentation.Controllers.Admin;

import com.utcn.Business.DataModels.CompositeProduct;
import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DeliveryService;
import com.utcn.Util.ExistentUniqueFieldException;
import com.utcn.Util.TableColumnAdapter;
import com.utcn.Util.ValueOutOfBoundsException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller for the administrator main table
 */
public class ControllerAdminTable implements ActionListener {
    private final DeliveryService deliveryService;
    private JTable mainTable;
    private JTextField textName;
    private JTextField textRating;
    private JTextField textCalories;
    private JTextField textProtein;
    private JTextField textFat;
    private JTextField textSodium;
    private JTextField textPrice;
    private final JPanel panel;

    public ControllerAdminTable(DeliveryService deliveryService, JPanel panel) {
        this.deliveryService = deliveryService;
        this.panel = panel;
    }

    public void setMainTable(JTable mainTable) {
        this.mainTable = mainTable;
        initializeTable();
    }

    public void setSearchFields (JTextField textName, JTextField textRating, JTextField textCalories,
                                 JTextField textProtein, JTextField textFat, JTextField textSodium,
                                 JTextField textPrice) {
        this.textName = textName;
        this.textRating = textRating;
        this.textCalories = textCalories;
        this.textProtein = textProtein;
        this.textFat = textFat;
        this.textSodium = textSodium;
        this.textPrice = textPrice;
    }

    /**
     * Initializes the table
     */
    private void initializeTable() {
        String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price", "Components"};
        List<MenuItem> menu = (List<MenuItem>) deliveryService.getMenu();
        DefaultTableModel tableModel = (DefaultTableModel) mainTable.getModel();

        tableModel.setColumnIdentifiers(columns);
        tableModel.setRowCount(0);

        for (MenuItem menuItem : menu) {
            tableModel.addRow(new String[]{menuItem.getTitle(), menuItem.getRating().toString(),
                    menuItem.getCalories().toString(), menuItem.getProtein().toString(), menuItem.getFat().toString(),
                    menuItem.getSodium().toString(), menuItem.getPrice().toString(),
                    CompositeProduct.decomposeProduct(menuItem)});
        }
        TableColumnAdapter.stretchColumns(mainTable);
        tableModel.fireTableDataChanged();
    }

    /**
     * Updates the table with the given data
     *
     * @param data  Data to appear in the table
     */
    public void updateTable(List<MenuItem> data) {
        DefaultTableModel tableModel = (DefaultTableModel) mainTable.getModel();
        tableModel.setRowCount(0);

        for (MenuItem menuItem : data) {
            tableModel.addRow(new String[]{menuItem.getTitle(), menuItem.getRating().toString(),
                    menuItem.getCalories().toString(), menuItem.getProtein().toString(), menuItem.getFat().toString(),
                    menuItem.getSodium().toString(), menuItem.getPrice().toString(),
                    CompositeProduct.decomposeProduct(menuItem)});
        }
        TableColumnAdapter.stretchColumns(mainTable);
        tableModel.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textName.getText().isBlank() || textRating.getText().isBlank() || textCalories.getText().isBlank() ||
                textProtein.getText().isBlank() || textFat.getText().isBlank() || textSodium.getText().isBlank() ||
                textPrice.getText().isBlank()) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "One or more fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            deliveryService.createBaseProduct(textName.getText(), Double.valueOf(textRating.getText()),
                    Integer.valueOf(textCalories.getText()), Integer.valueOf(textProtein.getText()),
                    Integer.valueOf(textFat.getText()), Integer.valueOf(textSodium.getText()),
                    Integer.valueOf(textPrice.getText()));
        } catch (ExistentUniqueFieldException err) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Name already exists", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ValueOutOfBoundsException err) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Value out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateTable((List<MenuItem>) deliveryService.getMenu());
    }
}
