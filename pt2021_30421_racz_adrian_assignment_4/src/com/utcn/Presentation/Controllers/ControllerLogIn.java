package com.utcn.Presentation.Controllers;

import com.utcn.Business.DataModels.UserData;
import com.utcn.Data.Serialization;
import com.utcn.Presentation.Frames.EmployeeFrame;
import com.utcn.Util.IntegerMutable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for logging in
 */
public class ControllerLogIn implements ActionListener {
    private static final String usersFile = "credentials.ser";

    private final IntegerMutable id;
    private final JTextField fieldUser;
    private final JPasswordField fieldPass;
    private final JPanel panel;
    private final JFrame clientFrame;
    private final JFrame adminFrame;
    private final JFrame employeeFrame;

    public ControllerLogIn(JTextField fieldUser, JPasswordField fieldPass, JPanel panel, JFrame clientFrame,
                           JFrame adminFrame, JFrame employeeFrame, IntegerMutable id) {
        this.fieldUser = fieldUser;
        this.fieldPass = fieldPass;
        this.panel = panel;
        this.employeeFrame = employeeFrame;
        this.adminFrame = adminFrame;
        this.clientFrame = clientFrame;
        this.id = id;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fieldUser.getText().equals("") || String.valueOf(fieldPass.getPassword()).equals("")) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "No empty fields allowed", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserData user = Serialization.deserializeUsers(usersFile).stream().
                filter(userData -> userData.getUsername().equalsIgnoreCase(fieldUser.getText())).
                findAny().orElse(null);

        if (user == null) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Username does not exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if (user.getPassword().equals(String.valueOf(fieldPass.getPassword()))){
            // Proceed
            id.setValue(user.getId());
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Log in successful\nId: " + user.getId() + "\nUser: " + user.getUsername() + "\nPass: " +
                            user.getPassword() + "\nType: " + user.getType(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            switch (user.getType()) {

                case client -> {
                    JOptionPane.getFrameForComponent(panel).setVisible(false);
                    clientFrame.setVisible(true);
                }
                case administrator -> {
                    JOptionPane.getFrameForComponent(panel).setVisible(false);
                    adminFrame.setVisible(true);
                }
                case employee -> {
                    ((EmployeeFrame)employeeFrame).employeeLogIn();
                    JOptionPane.getFrameForComponent(panel).setVisible(false);
                    employeeFrame.setVisible(true);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
