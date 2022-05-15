package com.utcn.Controllers;

import com.utcn.BussinessLogic.Operations;
import com.utcn.BussinessLogic.PatternMatching;
import com.utcn.DataModels.Polynomial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main controller, assigned to the "Calculate" JButton.
 *
 * It takes input from the two JTextFields and the operation from the other controller,
 * computes the result and shows it in the output JTextField.
 */
public class ControllerMain implements ActionListener {
    ControllerRadioButtons controllerRadioButtons;
    JTextField field1;
    JTextField field2;
    JTextField output;

    ///// Constructors //////////////////////////////////////////////////////////

    public ControllerMain(ControllerRadioButtons controllerRadioButtons,
                          JTextField field1, JTextField field2, JTextField output) {
        this.controllerRadioButtons = controllerRadioButtons;
        this.field1 = field1;
        this.field2 = field2;
        this.output = output;
    }

    ///// Overrides //////////////////////////////////////////////////////////////

    /**
     * Prints the result when the button is pressed.
     *
     * It takes input from the two JTextFields and converts it to Polynomials, then it takes the operation from the
     * other controller, computes the result with an Operations instance and shows it in the output JTextField.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Operations op = new Operations();
        PatternMatching patternMatching = new PatternMatching();
        Polynomial polynomial1 = patternMatching.extractPolynomial(field1.getText());
        Polynomial polynomial2 = patternMatching.extractPolynomial(field2.getText());

        switch (controllerRadioButtons.getOpConst()) {
            case ADDITION -> output.setText(op.polynomialAddition(polynomial1, polynomial2).toString());
            case SUBTRACTION -> output.setText(op.polynomialSubtraction(polynomial1, polynomial2).toString());
            case MULTIPLICATION -> output.setText(op.polynomialMultiplication(polynomial1, polynomial2).toString());
            case DIVISION -> output.setText(op.polynomialDivision(polynomial1, polynomial2).toString());
            case DERIVATION -> output.setText(op.polynomialDerivation(polynomial1).toString());
            case INTEGRATION -> output.setText(op.polynomialIntegration(polynomial1).toString());
        }
    }
}
