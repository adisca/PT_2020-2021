package com.utcn.Controllers;

import com.utcn.DataModels.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for "STOP" button
 */
public class AbortController implements ActionListener {

    SimulationManager simulationManager;
    JTextArea areaLog;

    public AbortController(SimulationManager simulationManager, JTextArea areaLog) {
        this.simulationManager = simulationManager;
        this.areaLog = areaLog;
    }

    /**
     * Stops the simulation if running
     *
     * @param e Action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (simulationManager.isRunning()) {
            simulationManager.stopSimulation();
            areaLog.append("Aborted simulation\n\n");
        }
        else
            areaLog.append("ERROR: Simulation is not running\n\n");
    }
}
