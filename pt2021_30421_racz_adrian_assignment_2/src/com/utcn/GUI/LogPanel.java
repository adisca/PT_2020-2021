package com.utcn.GUI;

import com.utcn.Controllers.SimulationController;

import javax.swing.*;
import java.awt.*;

/**
 * The panel for the log.
 * Contains the text area for the log, blame swing layouts for the existence of this separate class.
 */
public class LogPanel extends JPanel {
    public LogPanel(SimulationController simCtrl) {

        setLayout(new BorderLayout());

        JTextArea areaLog = new JTextArea();
        areaLog.setEditable(false);
        JScrollPane scrollableLog = new JScrollPane(areaLog);

        add(scrollableLog, BorderLayout.CENTER);

        simCtrl.setLog(areaLog);
    }
}
