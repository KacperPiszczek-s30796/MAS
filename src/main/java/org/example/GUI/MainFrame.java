package org.example.GUI;

import org.example.Entities.*;
import org.example.Enums.AuthMode;
import org.example.Enums.UserRole;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel, patientPanel, doctorPanel, paramedicPanel, driverPanel;
    private WelcomePanel welcomePanel;
    private LoginPanel loginPanel;
    private RoleChoicePanel loginRoleChoice;
    private RoleChoicePanel registerRoleChoice;
    private RegisterPanel registerPanel;

    public MainFrame() {
        setTitle("Emergency Transport System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPanel = new JPanel(new CardLayout());

        welcomePanel = new WelcomePanel(this);

        // TWO role choice panels
        loginRoleChoice = new RoleChoicePanel(this, AuthMode.LOGIN);
        registerRoleChoice = new RoleChoicePanel(this, AuthMode.REGISTER);

        loginPanel = new LoginPanel(this);

        // HOME PANELS
        patientPanel = new JPanel();
        doctorPanel = new JPanel();
        paramedicPanel = new JPanel();
        driverPanel = new JPanel();

        // ADD ALL PANELS
        contentPanel.add(welcomePanel, "WELCOME");
        contentPanel.add(loginRoleChoice, "ROLE_LOGIN");
        contentPanel.add(registerRoleChoice, "ROLE_REGISTER");
        contentPanel.add(loginPanel, "LOGIN");
        contentPanel.add(patientPanel, "PATIENT_HOME");
        contentPanel.add(doctorPanel, "DOCTOR_HOME");
        contentPanel.add(paramedicPanel, "PARAMEDIC_HOME");
        contentPanel.add(driverPanel, "DRIVER_HOME");

        add(contentPanel, BorderLayout.CENTER);

        showWelcome();
    }
    public void showRoleChoiceLogin() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "ROLE_LOGIN");
    }

    public void showRoleChoiceRegister() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "ROLE_REGISTER");
    }

    public void showWelcome() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "WELCOME");
    }

    public void showLogin(UserRole role) {
        loginPanel.setRole(role);
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "LOGIN");
    }

    public void showRegister(UserRole role) {
        registerPanel = new RegisterPanel(this, role);
        contentPanel.add(registerPanel, "REGISTER");
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "REGISTER");
    }


    public void showRoleChoice() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "ROLE");
    }

    public void showHome(UserRole role, Person user) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();

        switch (role) {
            case PATIENT:
                Patient_home.setUser((Patient) user);
                cl.show(contentPanel, "PATIENT_HOME");
                break;
            case DOCTOR:
                Doctor_home.setUser((Doctor) user);
                cl.show(contentPanel, "DOCTOR_HOME");
                break;
            case PARAMEDIC:
                Paramedic_home.setUser((Paramedic) user);
                cl.show(contentPanel, "PARAMEDIC_HOME");
                break;
            case DRIVER:
                Driver_home.setUser((Driver) user);
                cl.show(contentPanel, "DRIVER_HOME");
                break;
        }
    }

}
