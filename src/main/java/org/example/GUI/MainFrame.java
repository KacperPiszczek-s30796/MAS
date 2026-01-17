package org.example.GUI;

import org.example.Enums.UserRole;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel, patientPanel, doctorPanel, paramedicPanel, driverPanel;
    private WelcomePanel welcomePanel;
    private RoleChoicePanel roleChoicePanel;
    private LoginPanel loginPanel;

    public MainFrame() {
        setTitle("Emergency Transport System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPanel = new JPanel(new CardLayout());

        welcomePanel = new WelcomePanel(this);
        roleChoicePanel = new RoleChoicePanel(this);

        contentPanel.add(welcomePanel, "WELCOME");
        contentPanel.add(roleChoicePanel, "ROLE");

        add(contentPanel, BorderLayout.CENTER);

        loginPanel = new LoginPanel(this);
        patientPanel = new JPanel();
        doctorPanel = new JPanel();
        paramedicPanel = new JPanel();
        driverPanel = new JPanel();

        contentPanel.add(loginPanel, "LOGIN");
        contentPanel.add(patientPanel, "PATIENT_HOME");
        contentPanel.add(doctorPanel, "DOCTOR_HOME");
        contentPanel.add(paramedicPanel, "PARAMEDIC_HOME");
        contentPanel.add(driverPanel, "DRIVER_HOME");


        showWelcome();
    }

    public void showRoleChoice() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "ROLE");
    }

    public void showWelcome() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "WELCOME");
    }

    public void showLogin(UserRole role) {
        loginPanel.setRole(role);
        ((CardLayout) contentPanel.getLayout()).show(contentPanel, "LOGIN");
    }

    public void showHome(UserRole role) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();

        switch (role) {
            case PATIENT:
                cl.show(contentPanel, "PATIENT_HOME");
                break;
            case DOCTOR:
                cl.show(contentPanel, "DOCTOR_HOME");
                break;
            case PARAMEDIC:
                cl.show(contentPanel, "PARAMEDIC_HOME");
                break;
            case DRIVER:
                cl.show(contentPanel, "DRIVER_HOME");
                break;
        }
    }
}
