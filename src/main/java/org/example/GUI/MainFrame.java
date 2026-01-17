package org.example.GUI;

import org.example.Entities.*;
import org.example.Enums.AuthMode;
import org.example.Enums.UserRole;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private WelcomePanel welcomePanel;
    private LoginPanel loginPanel;
    private RoleChoicePanel loginRoleChoice;
    private RoleChoicePanel registerRoleChoice;
    private RegisterPanel registerPanel;
    private Paramedic_home paramedicHome;
    private Patient_home patientHome;
    private Doctor_home doctorHome;
    private Driver_home driverHome;

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
        paramedicHome = new Paramedic_home();
        patientHome = new Patient_home();
        doctorHome = new Doctor_home();
        driverHome = new Driver_home();

        // ADD ALL PANELS
        contentPanel.add(welcomePanel, "WELCOME");
        contentPanel.add(loginRoleChoice, "ROLE_LOGIN");
        contentPanel.add(registerRoleChoice, "ROLE_REGISTER");
        contentPanel.add(loginPanel, "LOGIN");
        contentPanel.add(patientHome, "PATIENT_HOME");
        contentPanel.add(doctorHome, "DOCTOR_HOME");
        contentPanel.add(paramedicHome, "PARAMEDIC_HOME");
        contentPanel.add(driverHome, "DRIVER_HOME");

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
                patientHome.setUser((Patient) user);
                cl.show(contentPanel, "PATIENT_HOME");
                break;
            case DOCTOR:
                doctorHome.setUser((Doctor) user);
                cl.show(contentPanel, "DOCTOR_HOME");
                break;
            case PARAMEDIC:
                paramedicHome.setUser((Paramedic) user);
                cl.show(contentPanel, "PARAMEDIC_HOME");
                break;
            case DRIVER:
                driverHome.setUser((Driver) user);
                cl.show(contentPanel, "DRIVER_HOME");
                break;
        }
    }

}
