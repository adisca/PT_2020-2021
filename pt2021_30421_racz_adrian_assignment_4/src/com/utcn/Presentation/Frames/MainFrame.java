package com.utcn.Presentation.Frames;

import com.utcn.Presentation.Panels.LogInPanel;
import com.utcn.Util.IntegerMutable;

import javax.swing.*;
import java.awt.*;

/**
 * Main frame.
 *
 * Completes some initializations for the GUI.
 * Creates the panel where all the interactions will take place.
 */
public class MainFrame extends JFrame {


    public MainFrame(String title, JFrame clientFrame, JFrame adminFrame, JFrame employeeFrame, IntegerMutable id) {
        super(title);

        setLayout(new BorderLayout());

        Container container = getContentPane();

        container.add(new LogInPanel(clientFrame, adminFrame, employeeFrame, id), BorderLayout.CENTER);
    }
}
