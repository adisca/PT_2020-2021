package com.utcn.GUI;

import com.utcn.Controllers.SimulationController;

import javax.swing.*;
import java.awt.*;

/**
 * Main frame.
 * Completes some initializations for the GUI.
 * Creates the panel where all the interactions will take place.
 */
public class MainFrame extends JFrame {

    public MainFrame(String title) {
        super(title);
        setLayout(new BorderLayout());

        SimulationController simCtrl = new SimulationController();

        Container container = getContentPane();

        container.add(new LogPanel(simCtrl), BorderLayout.CENTER);
        container.add(new MainPanel(simCtrl), BorderLayout.WEST);

    }
}
