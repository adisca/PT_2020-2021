package com.utcn.Controllers;

import com.utcn.DataModels.SimulationManager;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for writing in the textarea and the log file. It runs as a thread and is the one that starts the
 * SimulationManager thread.
 */
public class WriteController implements Runnable{
    private static final String FILE_PATH = "log.txt";

    private SimulationManager simManager;
    private ArrayList<JRadioButton> radioButtons;
    private JTextArea areaLog;

    public WriteController(SimulationManager simManager, ArrayList<JRadioButton> radioButtons, JTextArea areaLog) {
        this.simManager = simManager;
        this.radioButtons = radioButtons;
        this.areaLog = areaLog;
    }

    /**
     * Waits for new messages to display, until the simulation stops
     */
    @Override
    public void run() {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            String msg;
            simManager.startSimulation();
            while (simManager.isRunning() || simManager.getMessages().size() != 0) {
                while (simManager.isRunning() && simManager.getMessages().size() == 0) {
                    Thread.sleep(10);
                }
                if (!simManager.isRunning() && simManager.getMessages().size() == 0)
                    Thread.sleep(1100);
                msg = simManager.getMessages().take();
                writer.write(msg);
                areaLog.append(msg);
                areaLog.setCaretPosition(areaLog.getDocument().getLength());
            }
            writer.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        for (JRadioButton radioButton : radioButtons) {
            radioButton.setVisible(true);
        }
    }
}
