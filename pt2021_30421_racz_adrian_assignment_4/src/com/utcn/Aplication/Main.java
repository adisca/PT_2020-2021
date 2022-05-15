package com.utcn.Aplication;

import com.utcn.Business.DeliveryService;
import com.utcn.Presentation.Frames.AdminFrame;
import com.utcn.Presentation.Frames.ClientFrame;
import com.utcn.Presentation.Frames.EmployeeFrame;
import com.utcn.Presentation.Frames.MainFrame;
import com.utcn.Util.IntegerMutable;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Starts a thread for the Swing graphical interface and completes the setup for the frame.
             */
            @Override
            public void run() {
                IntegerMutable id = new IntegerMutable();
                DeliveryService deliveryService = new DeliveryService();

                JFrame clientFrame = new ClientFrame("Client", id, deliveryService);
                clientFrame.setSize(1400, 800);
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clientFrame.setVisible(false);

                JFrame adminFrame = new AdminFrame("Administrator", deliveryService);
                adminFrame.setSize(1400, 800);
                adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                adminFrame.setVisible(false);

                JFrame employeeFrame = new EmployeeFrame("Employee", deliveryService);
                employeeFrame.setSize(1400, 800);
                employeeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                employeeFrame.setVisible(false);

                JFrame frame = new MainFrame("Food delivery management system", clientFrame, adminFrame,
                        employeeFrame, id);
                frame.setSize(500, 300);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
