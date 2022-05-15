package com.utcn;

import com.utcn.GUI.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * Starts a thread for the Swing graphical interface and completes the setup for the frame.
			 */
			@Override
			public void run() {
				JFrame frame = new MainFrame("Queues Simulator");
				frame.setSize(500, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});

    }
}
