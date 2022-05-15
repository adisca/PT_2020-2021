package com.utcn.GUI;


import com.utcn.Controllers.ControllerMain;
import com.utcn.Controllers.ControllerRadioButtons;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * The main panel that contains all the GUI elements.
 *
 * Creates the elements, sets their positions and binds them to their respective controllers.
 */
public class MainPanel extends JPanel {
    public MainPanel() {

        ///// Declarations //////////////////////////////////////////

        JLabel label1 = new JLabel("First Polynomial: ");
        JLabel label2 = new JLabel("Second Polynomial: ");
        JLabel labelResult = new JLabel("Result: ");

        JTextField field1 = new JTextField(10);
        JTextField field2 = new JTextField(10);
        JTextField fieldResult = new JTextField(10);
        fieldResult.setEditable(false);

        JButton buttonCompute = new JButton("Calculate");

        JRadioButton buttonAdd = new JRadioButton("Addition");
        JRadioButton buttonSubtract = new JRadioButton("Subtraction");
        JRadioButton buttonMultiply = new JRadioButton("Multiplication");
        JRadioButton buttonDivide = new JRadioButton("Division");
        JRadioButton buttonDerivate = new JRadioButton("Derivation");
        JRadioButton buttonIntegrate = new JRadioButton("Integration");

        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(buttonAdd);
        buttonGroup.add(buttonSubtract);
        buttonGroup.add(buttonMultiply);
        buttonGroup.add(buttonDivide);
        buttonGroup.add(buttonDerivate);
        buttonGroup.add(buttonIntegrate);
        buttonAdd.setSelected(true);

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        ///// Layout ////////////////////////////////////////////////

        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;

        // 1st row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(label1, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(field1, gridBagConstraints);

        // 2nd row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(label2, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(field2, gridBagConstraints);

        // 3rd row

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(labelResult, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(fieldResult, gridBagConstraints);

        // 4rd row

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(buttonCompute, gridBagConstraints);


        // 5th row

        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        add(buttonAdd, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        add(buttonSubtract, gridBagConstraints);

        // 6th row

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        add(buttonMultiply, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        add(buttonDivide, gridBagConstraints);

        // 7th row

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        add(buttonDerivate, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        add(buttonIntegrate, gridBagConstraints);

        ///// Controllers /////////////////////////////////////////

        // Vector with all the radio buttons
        // Packs them up to be sent to their controller.
        Vector<JRadioButton> radioButtons = new Vector<>();

        radioButtons.add(buttonAdd);
        radioButtons.add(buttonSubtract);
        radioButtons.add(buttonMultiply);
        radioButtons.add(buttonDivide);
        radioButtons.add(buttonDerivate);
        radioButtons.add(buttonIntegrate);

        // Controller for the JRadioButtons, also added as their action listener
        ControllerRadioButtons controllerRadioButtons = new ControllerRadioButtons(radioButtons, field2, label2);
        buttonAdd.addActionListener(controllerRadioButtons);
        buttonSubtract.addActionListener(controllerRadioButtons);
        buttonMultiply.addActionListener(controllerRadioButtons);
        buttonDivide.addActionListener(controllerRadioButtons);
        buttonDerivate.addActionListener(controllerRadioButtons);
        buttonIntegrate.addActionListener(controllerRadioButtons);

        // Controller for the JButton, added as its action listener
        ControllerMain controllerMain = new ControllerMain(controllerRadioButtons,
                field1, field2, fieldResult);
        buttonCompute.addActionListener(controllerMain);

    }
}
