package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private WelcomePanel welcomePanel;
    private RoleChoicePanel roleChoicePanel;

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
}
