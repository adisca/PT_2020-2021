package com.utcn.Controllers;

import com.utcn.DataModels.OperationsConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Controller for all the JRadioButtons.
 *
 * It chooses the OperationsConstants that denotes the operation to be performed.
 * Also, it hides the second input label and text box when not needed.
 */
public class ControllerRadioButtons implements ActionListener {

    private Vector<JRadioButton> radioButtons;
    private OperationsConstants opConst;
    private JTextField secondaryField;
    private JLabel secondaryLabel;

    ///// Constructors ///////////////////////////////////////////////////////////

    public ControllerRadioButtons(Vector<JRadioButton> radioButtons, JTextField secondaryField, JLabel secondaryLabel) {
        this.radioButtons = radioButtons;
        this.opConst = OperationsConstants.ADDITION;
        this.secondaryField = secondaryField;
        this.secondaryLabel = secondaryLabel;
    }

    ///// Getters ////////////////////////////////////////////////////////////////

    public OperationsConstants getOpConst() {
        return opConst;
    }

    ///// Overrides //////////////////////////////////////////////////////////////

    /**
     * Chooses the OperationsConstants when a radio button is clicked.
     *
     * Also, it hides the second input label and text box when not needed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        secondaryField.setVisible(true);
        secondaryLabel.setVisible(true);
        if (radioButtons.get(0).isSelected()) {
            opConst = OperationsConstants.ADDITION;
        } else if (radioButtons.get(1).isSelected()) {
            opConst = OperationsConstants.SUBTRACTION;
        } else if (radioButtons.get(2).isSelected()) {
            opConst = OperationsConstants.MULTIPLICATION;
        } else if (radioButtons.get(3).isSelected()) {
            opConst = OperationsConstants.DIVISION;
        } else if (radioButtons.get(4).isSelected()) {
            opConst = OperationsConstants.DERIVATION;
            secondaryField.setVisible(false);
            secondaryLabel.setVisible(false);
        } else if (radioButtons.get(5).isSelected()) {
            opConst = OperationsConstants.INTEGRATION;
            secondaryField.setVisible(false);
            secondaryLabel.setVisible(false);
        }
    }
}
