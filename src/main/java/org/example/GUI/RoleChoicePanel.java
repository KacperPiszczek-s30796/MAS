package org.example.GUI;

import org.example.Enums.UserRole;

import javax.swing.*;
import java.awt.*;

public class RoleChoicePanel extends JPanel {

    public RoleChoicePanel(MainFrame frame) {
        setBounds(0, 0, 600, 400);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Choose your role", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JButton patientButton = new JButton("Patient");
        JButton doctorButton = new JButton("Doctor");
        JButton paramedicButton = new JButton("Paramedic");
        JButton driverButton = new JButton("Driver");

        buttonsPanel.add(patientButton);
        buttonsPanel.add(doctorButton);
        buttonsPanel.add(paramedicButton);
        buttonsPanel.add(driverButton);

        add(buttonsPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.showWelcome());
        add(backButton, BorderLayout.SOUTH);

        patientButton.addActionListener(e -> frame.showLogin(UserRole.PATIENT));
        doctorButton.addActionListener(e -> frame.showLogin(UserRole.DOCTOR));
        paramedicButton.addActionListener(e -> frame.showLogin(UserRole.PARAMEDIC));
        driverButton.addActionListener(e -> frame.showLogin(UserRole.DRIVER));
    }
}
