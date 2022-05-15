package com.utcn.Presentation.Frames;

import com.utcn.Business.DeliveryService;
import com.utcn.Presentation.Panels.EmployeePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Employee frame
 */
public class EmployeeFrame extends JFrame {
    private EmployeePanel employeePanel;

    public EmployeeFrame(String title, DeliveryService deliveryService) {
        super(title);

        setLayout(new BorderLayout());

        Container container = getContentPane();

        employeePanel = new EmployeePanel(deliveryService);

        container.add(employeePanel, BorderLayout.CENTER);
    }

    public void employeeLogIn() {
        employeePanel.employeeLogin();
    }
}
