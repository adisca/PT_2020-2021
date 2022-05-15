package com.utcn.Presentation.Panels;

import com.utcn.Presentation.Controllers.ControllerLogIn;
import com.utcn.Presentation.Controllers.ControllerRegister;
import com.utcn.Util.IntegerMutable;

import javax.swing.*;
import java.awt.*;

/**
 * The panel of the log in screen
 */
public class LogInPanel extends JPanel {

    public LogInPanel(JFrame clientFrame, JFrame adminFrame, JFrame employeeFrame, IntegerMutable id) {
        JButton btnRegister = new JButton("Register");
        JButton btnLogin = new JButton("Log in");

        JLabel labelUser = new JLabel("Username: ");
        JLabel labelPass = new JLabel("Password: ");

        JTextField fieldUser = new JTextField(20);
        JPasswordField fieldPass = new JPasswordField(20);

        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(labelUser, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(fieldUser, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(labelPass, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(fieldPass, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LINE_END;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(btnRegister, gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(btnLogin, gridBagConstraints);

        btnLogin.addActionListener(new ControllerLogIn(fieldUser, fieldPass, this, clientFrame, adminFrame,
                employeeFrame, id));
        btnRegister.addActionListener(new ControllerRegister(fieldUser, fieldPass, this));
    }

}
