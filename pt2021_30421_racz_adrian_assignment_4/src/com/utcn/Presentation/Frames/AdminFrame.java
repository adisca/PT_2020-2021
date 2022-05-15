package com.utcn.Presentation.Frames;

import com.utcn.Business.DeliveryService;
import com.utcn.Presentation.Panels.AdminPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Administrator Frame
 */
public class AdminFrame extends JFrame {

    public AdminFrame(String title, DeliveryService deliveryService) {
        super(title);

        setLayout(new BorderLayout());

        Container container = getContentPane();

        container.add(new AdminPanel(deliveryService), BorderLayout.CENTER);
    }
}
