package com.utcn.Controllers;

import com.utcn.BusinessLogic.TextBoxValidator;
import com.utcn.DataModels.SimulationManager;
import com.utcn.Exceptions.MinMaxException;
import com.utcn.Exceptions.NumberNegativeException;
import com.utcn.Exceptions.NumberZeroException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller for "START" button
 */
public class SimulationController implements ActionListener {
    private JTextArea areaLog;
    private JTextField fieldSimTime;
    private JTextField fieldQueues;
    private JTextField fieldClients;
    private JTextField fieldArrivalMin;
    private JTextField fieldArrivalMax;
    private JTextField fieldServiceMin;
    private JTextField fieldServiceMax;
    private SimulationManager simManager;
    private ArrayList<JRadioButton> radioButtons;

    public SimulationController() {
        simManager = new SimulationManager();
    }

    public SimulationManager getSimManager() {
        return simManager;
    }

    public JTextArea getAreaLog() {
        return areaLog;
    }

    public void setLog(JTextArea areaLog) {
        this.areaLog = areaLog;
    }

    public void setTextFields(JTextField fieldSimTime, JTextField fieldQueues, JTextField fieldClients,
                              JTextField fieldArrivalMin, JTextField fieldArrivalMax, JTextField fieldServiceMin,
                              JTextField fieldServiceMax) {
        this.fieldSimTime = fieldSimTime;
        this.fieldQueues = fieldQueues;
        this.fieldClients = fieldClients;
        this.fieldArrivalMin = fieldArrivalMin;
        this.fieldArrivalMax = fieldArrivalMax;
        this.fieldServiceMin = fieldServiceMin;
        this.fieldServiceMax = fieldServiceMax;
    }

    public void setRadioButtons(ArrayList<JRadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    /**
     * Starts the simulation.
     * First it checks if the simulation is running, then it validates the textboxes and initializes
     * the SimulationManager with that data. At the end it creates a WriteController thread and starts it.
     *
     * @param e Action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (simManager.isRunning()) {
            areaLog.append("ERROR: Simulation is already running\n\n");
            return;
        }
        areaLog.setText("");
        try {
            new TextBoxValidator().validate(fieldSimTime.getText(), fieldQueues.getText(), fieldClients.getText(),
                    fieldArrivalMin.getText(), fieldArrivalMax.getText(), fieldServiceMin.getText(),
                    fieldServiceMax.getText());
        } catch (NumberFormatException numberFormatException) {
            areaLog.append("ERROR: Only numbers must be introduced\n\n");
            return;
        } catch (NumberNegativeException numberNegativeException) {
            areaLog.append("ERROR: Numbers must be positive\n\n");
            return;
        } catch (MinMaxException minMaxException) {
            areaLog.append("ERROR: Max arrival time or Max service time is less than their Min counterpart\n\n");
            return;
        } catch (NumberZeroException numberZeroException) {
            areaLog.append("ERROR: Simulation time, min or max service times, number of queues or clients are zero\n\n");
            return;
        }
        for (JRadioButton radioButton : radioButtons) {
            radioButton.setVisible(false);
        }
        simManager.initialize(Integer.parseInt(fieldSimTime.getText()),
                Integer.parseInt(fieldQueues.getText()), Integer.parseInt(fieldClients.getText()),
                Integer.parseInt(fieldArrivalMin.getText()), Integer.parseInt(fieldArrivalMax.getText()),
                Integer.parseInt(fieldServiceMin.getText()), Integer.parseInt(fieldServiceMax.getText()));
        Thread thread = new Thread(new WriteController(simManager, radioButtons, areaLog));
        thread.start();
    }

}
