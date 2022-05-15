package com.utcn.Presentation.Controllers.Client;

import com.utcn.Business.DataModels.MenuItem;
import com.utcn.Business.DeliveryService;
import com.utcn.Data.MyFileWriter;
import com.utcn.Util.IntegerMutable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that manages the creation of client orders
 */
public class ControllerOrder implements ActionListener {
    private static final String billFile = "bill.txt";

    private final IntegerMutable id;
    private final DeliveryService deliveryService;
    private JTable orderTable;
    private JPanel panel;

    public ControllerOrder(DeliveryService deliveryService, JPanel panel, IntegerMutable id) {
        this.deliveryService = deliveryService;
        this.id = id;
        this.panel = panel;
    }

    public void setOrderTable(JTable orderTable) {
        this.orderTable = orderTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<MenuItem> orderList = new ArrayList<>();
        for (int i = 0; i < orderTable.getModel().getRowCount(); i++) {
            orderList.add(deliveryService.searchByName(
                    orderTable.getValueAt(i, 0).toString()));
        }
        if (orderList.size() < 1)
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Empty order",
                    "Error", JOptionPane.ERROR_MESSAGE);
        else {
            Integer price = deliveryService.createOrder(id.getValue(), orderList);
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Order successful.\nTotal price: " + price,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            MyFileWriter.createBill(billFile, orderList, price, id.getValue());
        }
    }
}
