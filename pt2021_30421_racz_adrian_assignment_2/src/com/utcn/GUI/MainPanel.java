package com.utcn.GUI;

import com.utcn.Controllers.AbortController;
import com.utcn.Controllers.SimulationController;
import com.utcn.Controllers.StrategyController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The main panel that contains all the GUI elements, except the textarea for the log.
 * Creates the elements, sets their positions and binds them to their respective controllers.
 */
public class MainPanel extends JPanel {
    public MainPanel(SimulationController simCtrl) {

        ///// Declarations //////////////////////////////////////////

        JLabel labelSimTime = new JLabel("Max simulation time: ");
        JLabel labelQueues = new JLabel("Nb queues: ");
        JLabel labelClients = new JLabel("Nb clients: ");
        JLabel labelArrivalMin = new JLabel("Min arrival time: ");
        JLabel labelArrivalMax = new JLabel("Max arrival time: ");
        JLabel labelServiceMin = new JLabel("Min service time: ");
        JLabel labelServiceMax = new JLabel("Max service time: ");

        JTextField fieldSimTime = new JTextField(5);
        JTextField fieldQueues = new JTextField(5);
        JTextField fieldClients = new JTextField(5);
        JTextField fieldArrivalMin = new JTextField(5);
        JTextField fieldArrivalMax = new JTextField(5);
        JTextField fieldServiceMin = new JTextField(5);
        JTextField fieldServiceMax = new JTextField(5);

        // A "default setting", useful for my laziness when testing
//        fieldSimTime.setText("30");
//        fieldQueues.setText("2");
//        fieldClients.setText("7");
//        fieldArrivalMin.setText("0");
//        fieldArrivalMax.setText("10");
//        fieldServiceMin.setText("3");
//        fieldServiceMax.setText("8");

        JButton buttonStart = new JButton("START");
        JButton buttonStop = new JButton("STOP");

        JRadioButton radioBtnFast = new JRadioButton("Strategy Fast");
        JRadioButton radioBtnShort = new JRadioButton("Strategy Short");
        radioBtnFast.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(radioBtnFast);
        buttonGroup.add(radioBtnShort);

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        ///// Layout ////////////////////////////////////////////////

        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        // 1st row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(labelSimTime, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(fieldSimTime, gridBagConstraints);

        // 2nd row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(labelQueues, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(fieldQueues, gridBagConstraints);

        // 3rd row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(labelClients, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(fieldClients, gridBagConstraints);

        // 4rd row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(labelArrivalMin, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(fieldArrivalMin, gridBagConstraints);


        // 5th row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        add(labelArrivalMax, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        add(fieldArrivalMax, gridBagConstraints);

        // 6th row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        add(labelServiceMin, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        add(fieldServiceMin, gridBagConstraints);

        // 7th row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        add(labelServiceMax, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        add(fieldServiceMax, gridBagConstraints);

        // 8th row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        add(radioBtnFast, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        add(radioBtnShort, gridBagConstraints);

        // 9th row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        add(buttonStop, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        add(buttonStart, gridBagConstraints);

        ///// Controllers /////////////////////////////////////////

        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        radioButtons.add(radioBtnFast);
        radioButtons.add(radioBtnShort);

        AbortController abortCtrl = new AbortController(simCtrl.getSimManager(), simCtrl.getAreaLog());
        StrategyController stratCtrl = new StrategyController(radioButtons, simCtrl.getSimManager());
        simCtrl.setTextFields(fieldSimTime, fieldQueues, fieldClients, fieldArrivalMin, fieldArrivalMax,
                fieldServiceMin, fieldServiceMax);
        simCtrl.setRadioButtons(radioButtons);

        buttonStart.addActionListener(simCtrl);
        buttonStop.addActionListener(abortCtrl);
        radioBtnFast.addActionListener(stratCtrl);
        radioBtnShort.addActionListener(stratCtrl);

    }
}
