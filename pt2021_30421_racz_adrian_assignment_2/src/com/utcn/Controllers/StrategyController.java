package com.utcn.Controllers;

import com.utcn.DataModels.QueuePolicy;
import com.utcn.DataModels.SimulationManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller for strategy radio buttons
 */
public class StrategyController implements ActionListener {
    private ArrayList<JRadioButton> radioButtons;
    private SimulationManager simManager;

    public StrategyController(ArrayList<JRadioButton> radioButtons, SimulationManager simManager) {
        this.radioButtons = radioButtons;
        this.simManager = simManager;
    }

    /**
     * Changes the strategy
     *
     * @param e Action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (radioButtons.get(0).isSelected()) {
            simManager.getScheduler().chooseStrategy(QueuePolicy.FASTEST_TIME);
        } else if (radioButtons.get(1).isSelected()) {
            simManager.getScheduler().chooseStrategy(QueuePolicy.SHORTEST_QUEUE);
        }
    }
}
