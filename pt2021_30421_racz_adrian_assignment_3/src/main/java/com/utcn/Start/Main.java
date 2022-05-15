package com.utcn.Start;

import com.utcn.Presentation.GUI.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Starts a thread for the Swing graphical interface and completes the setup for the frame.
             */
            @Override
            public void run() {
                JFrame frame = new MainFrame("Order Management");
                frame.setSize(1300, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
