package com.utcn.Presentation.GUI;

import com.utcn.Presentation.GUI.Panels.ClientPanel;
import com.utcn.Presentation.GUI.Panels.OrderPanel;
import com.utcn.Presentation.GUI.Panels.ProductPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main frame.
 *
 * Completes some initializations for the GUI.
 * Creates the panels where all the interactions will take place.
 */
public class MainFrame extends JFrame {


    public MainFrame(String title) {
        super(title);

        // Set layout manager
        setLayout(new BorderLayout());

        //Add Swing components to content pane
        Container container = getContentPane();

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Clients", null, new ClientPanel(), "For client management");
        tabbedPane.addTab("Products", null, new ProductPanel(), "For product management");
        tabbedPane.addTab("Orders", null, new OrderPanel(), "For order management");

        container.add(tabbedPane, BorderLayout.CENTER);
    }
}
