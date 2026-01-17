package org.example.GUI;

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

        patientButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Patient selected"));
        doctorButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Doctor selected"));
        paramedicButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Paramedic selected"));
        driverButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Driver selected"));
    }
}
