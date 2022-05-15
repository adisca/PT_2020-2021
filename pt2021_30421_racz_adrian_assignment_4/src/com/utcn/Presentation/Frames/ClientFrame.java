package com.utcn.Presentation.Frames;

import com.utcn.Business.DeliveryService;
import com.utcn.Presentation.Panels.ClientPanel;
import com.utcn.Util.IntegerMutable;

import javax.swing.*;
import java.awt.*;

/**
 * Client frame
 */
public class ClientFrame extends JFrame {

    public ClientFrame(String title, IntegerMutable id, DeliveryService deliveryService) {
        super(title);

        setLayout(new BorderLayout());

        Container container = getContentPane();

        container.add(new ClientPanel(id, deliveryService), BorderLayout.CENTER);
    }
}
