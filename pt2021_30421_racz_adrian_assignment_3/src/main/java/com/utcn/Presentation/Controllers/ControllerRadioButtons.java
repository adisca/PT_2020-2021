package com.utcn.Presentation.Controllers;

import com.utcn.Model.Enums.OperationsConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller for the radio buttons
 */
public class ControllerRadioButtons implements ActionListener {
    private ArrayList<JRadioButton> radioButtons;
    private ArrayList<JLabel> labels;
    private ArrayList<JTextField> textFields;
    private OperationsConstants opConst;
    private JLabel labelTargetId;
    private JTextField textFieldTargetId;

    public ControllerRadioButtons() {
        radioButtons = null;
        labels = null;
        textFields = null;
        labelTargetId = null;
        textFieldTargetId = null;
        opConst = OperationsConstants.ADD;
    }

    public void setTextFieldsAndLabels(ArrayList<JLabel> labels, ArrayList<JTextField> textFields, JLabel labelTargetId,
                                       JTextField textFieldTargetId) {
        this.labels = labels;
        this.textFields = textFields;
        this.labelTargetId = labelTargetId;
        this.textFieldTargetId = textFieldTargetId;
    }

    public void setRadioButtons(ArrayList<JRadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    public OperationsConstants getOpConst() {
        return opConst;
    }

    /**
     * Sets the opConst accordingly and changes the visibility of the fields for easier interface usage
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (radioButtons.get(0).isSelected()) {
            opConst = OperationsConstants.ADD;
            for (JLabel label : labels) {
                label.setVisible(true);
            }
            for (JTextField textField : textFields) {
                textField.setVisible(true);
            }
            labelTargetId.setVisible(false);
            textFieldTargetId.setVisible(false);
        } else if (radioButtons.get(1).isSelected()) {
            opConst = OperationsConstants.EDIT;
            for (JLabel label : labels) {
                label.setVisible(true);
            }
            for (JTextField textField : textFields) {
                textField.setVisible(true);
            }
            labelTargetId.setVisible(true);
            textFieldTargetId.setVisible(true);
        } else if (radioButtons.get(2).isSelected()) {
            opConst = OperationsConstants.DELETE;
            for (JLabel label : labels) {
                label.setVisible(false);
            }
            for (JTextField textField : textFields) {
                textField.setVisible(false);
            }
            labelTargetId.setVisible(true);
            textFieldTargetId.setVisible(true);
        }
    }
}
