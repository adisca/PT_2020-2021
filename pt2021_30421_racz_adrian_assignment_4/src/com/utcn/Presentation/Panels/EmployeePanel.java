package com.utcn.Presentation.Panels;

import com.utcn.Business.DeliveryService;
import com.utcn.Presentation.Controllers.ControllerEmployee;

import javax.swing.*;
import java.awt.*;

/**
 * The main panel of the employees.
 *
 * Contains 2 tables, the one on the left with old orders,
 * and the one on the right with new orders
 */
public class EmployeePanel extends JPanel {
    private ControllerEmployee controllerEmployee;

    public EmployeePanel(DeliveryService deliveryService) {
        JTable oldTable = new JTable();
        JTable newTable = new JTable();
        controllerEmployee = new ControllerEmployee(oldTable, newTable, deliveryService);

        setLayout(new BorderLayout());

        add(new JScrollPane(oldTable), BorderLayout.WEST);
        add(new JScrollPane(newTable), BorderLayout.EAST);

    }

    public void employeeLogin() {
        controllerEmployee.employeeLogIn();
    }
}
