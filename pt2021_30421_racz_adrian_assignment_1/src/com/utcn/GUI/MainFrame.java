package com.utcn.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Main frame.
 *
 * Completes some initializations for the GUI.
 * Creates the panel where all the interactions will take place.
 */
public class MainFrame extends JFrame {

    public MainFrame(String title) {
        super(title);

        // Set layout manager
        setLayout(new BorderLayout());

        //Add Swing components to content pane
        Container container = getContentPane();

        container.add(new MainPanel(), BorderLayout.CENTER);

    }
}
