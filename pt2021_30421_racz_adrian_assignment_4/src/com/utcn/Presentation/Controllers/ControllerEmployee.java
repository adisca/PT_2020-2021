package com.utcn.Presentation.Controllers;

import com.utcn.Business.DataModels.CompositeProduct;
import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DataModels.Order;
import com.utcn.Business.DeliveryService;
import com.utcn.Data.Serialization;
import com.utcn.Util.TableColumnAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Controller for the employee tables
 */
public class ControllerEmployee implements Observer {
    private static final String oldOrdersFile = "employeeOrdersOld.ser";
    private static final String newOrdersFile = "employeeOrdersNew.ser";

    private DeliveryService deliveryService;
    private List<Order> oldOrders;
    private List<Order> newOrders;
    private JTable oldTable;
    private JTable newTable;

    public ControllerEmployee(JTable oldTable, JTable newTable, DeliveryService deliveryService) {
        this.oldOrders = Serialization.deserializeEmployeeOrders(oldOrdersFile);
        this.newOrders = Serialization.deserializeEmployeeOrders(newOrdersFile);
        this.deliveryService = deliveryService;
        this.deliveryService.addObserver(this);
        this.oldTable = oldTable;
        this.newTable = newTable;
        initializeTable(oldTable);
        initializeTable(newTable);
    }

    /**
     * Initializes a table
     *
     * @param table The table
     */
    private void initializeTable(JTable table) {
        table.setModel(new DefaultTableModel());
        String[] columns = {"OrderID", "ClientID", "Time", "Products"};
        ((DefaultTableModel) table.getModel()).setColumnIdentifiers(columns);
        TableColumnAdapter.stretchColumns(table);
        ((DefaultTableModel) table.getModel()).fireTableDataChanged();
    }

    /**
     * Updates the tables with the newest info, then sets all new orders to old orders (for the next run)
     */
    public void employeeLogIn() {
        updateTable(newTable, newOrders);
        updateTable(oldTable, oldOrders);
        oldOrders.addAll(newOrders);
        newOrders.clear();
        Serialization.serialize(oldOrdersFile, (Serializable) oldOrders);
        Serialization.serialize(newOrdersFile, (Serializable) newOrders);
    }

    /**
     * Updates a table with the given data
     *
     * @param table The table
     * @param data  The data
     */
    public void updateTable(JTable table, List<Order> data) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        for (Order order : data) {
            StringBuilder command = new StringBuilder();
            for (MenuItem menuItem : deliveryService.getOrders().get(order)) {
                command.append(menuItem.getTitle()).append(", ");
            }
            if (command.length() >= 2)
                command.setLength(command.length() - 2);

            tableModel.addRow(new String[]{order.getOrderID().toString(), order.getClientID().toString(),
                    order.getOrderDate().toString(), command.toString()});
        }
        TableColumnAdapter.stretchColumns(table);
        tableModel.fireTableDataChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Order) {
            newOrders.add((Order) arg);
            Serialization.serialize(newOrdersFile, (Serializable) newOrders);
        }
        else
            System.out.println("Something strange is happening");
    }
}
