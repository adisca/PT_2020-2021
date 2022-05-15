package com.utcn.Presentation.Controllers;

import com.utcn.Business.DataModels.UserData;
import com.utcn.Business.DataModels.UserType;
import com.utcn.Data.Serialization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the registration.
 * Tip: any name that starts with admin will be registered as an administrator
 * and any name starting with mply will be registered as an employee
 */
public class ControllerRegister implements ActionListener {
    private static final String usersFile = "credentials.ser";

    private JTextField fieldUser;
    private JPasswordField fieldPass;
    private JPanel panel;

    public ControllerRegister(JTextField fieldUser, JPasswordField fieldPass, JPanel panel) {
        this.fieldUser = fieldUser;
        this.fieldPass = fieldPass;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (fieldUser.getText().equals("") || String.valueOf(fieldPass.getPassword()).equals("")) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "No empty fields allowed", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<UserData> users = Serialization.deserializeUsers(usersFile);

        if (users.stream().anyMatch(userData -> userData.getUsername().equalsIgnoreCase(fieldUser.getText()))) {
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            Integer maxID = 0;
            for (UserData user : users) {
                if (user.getId() >= maxID) {
                    maxID = user.getId() + 1;
                }
            }
            if (fieldUser.getText().toLowerCase().indexOf("admin") == 0) {
                users.add(new UserData(maxID, fieldUser.getText(), String.valueOf(fieldPass.getPassword()),
                        UserType.administrator));
            }
            else if (fieldUser.getText().toLowerCase().indexOf("mply") == 0) {
                users.add(new UserData(maxID, fieldUser.getText(), String.valueOf(fieldPass.getPassword()),
                        UserType.employee));
            } else {
                users.add(new UserData(maxID, fieldUser.getText(), String.valueOf(fieldPass.getPassword()),
                        UserType.client));
            }
            JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(panel),
                    "Registration successful\nId: " + users.get(users.size() - 1).getId()
                            + "\nUser: " + users.get(users.size() - 1).getUsername() +
                            "\nPass: " + users.get(users.size() - 1).getPassword() +
                            "\nType: " + users.get(users.size() - 1).getType().name(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            Serialization.serialize(usersFile, (ArrayList<UserData>) users);
        }
    }
}
