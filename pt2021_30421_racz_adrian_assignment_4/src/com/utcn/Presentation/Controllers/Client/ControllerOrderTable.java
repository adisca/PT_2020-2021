package com.utcn.Presentation.Controllers.Client;

import com.utcn.Business.DataModels.CompositeProduct;
import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DeliveryService;
import com.utcn.Util.TableColumnAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the client order table
 */
public class ControllerOrderTable implements ActionListener {
    private final DeliveryService deliveryService;
    private JTable mainTable;
    private JTable orderTable;
    private final JPanel panel;

    public ControllerOrderTable(DeliveryService deliveryService, JPanel panel) {
        this.deliveryService = deliveryService;
        this.panel = panel;
    }

    public void setMainTable(JTable mainTable) {
        this.mainTable = mainTable;
    }

    /**
     * Sets and initializes the order table
     *
     * @param orderTable    The order table
     */
    public void setOrderTable(JTable orderTable) {
        this.orderTable = orderTable;
        String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price", "Components"};
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();

        tableModel.setColumnIdentifiers(columns);
        tableModel.setRowCount(0);
        TableColumnAdapter.stretchColumns(orderTable);
        tableModel.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainTable.getSelectedRow() >= 0) {
            DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();

            MenuItem menuItem = deliveryService.searchByName(
                    mainTable.getValueAt(mainTable.getSelectedRow(), 0).toString());
            if (menuItem == null) {
                JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                        "UNEXPECTED ERROR", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.addRow(new String[]{menuItem.getTitle(),
                    menuItem.getRating().toString(), menuItem.getCalories().toString(),
                    menuItem.getProtein().toString(), menuItem.getFat().toString(),
                    menuItem.getSodium().toString(), menuItem.getPrice().toString(),
                    CompositeProduct.decomposeProduct(menuItem)});
            TableColumnAdapter.stretchColumns(orderTable);
            tableModel.fireTableDataChanged();
        }
        else {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "No table row has been selected!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
