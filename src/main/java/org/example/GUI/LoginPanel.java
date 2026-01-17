package org.example.GUI;

import org.example.Enums.UserRole;
import org.example.Utilities.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final JTextField nameField = new JTextField(15);
    private final JTextField surnameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel errorLabel = new JLabel(" ");

    private UserRole role;
    private final MainFrame frame;

    public LoginPanel(MainFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Name:"), c);
        c.gridx = 1;
        add(nameField, c);

        c.gridx = 0; c.gridy = 1;
        add(new JLabel("Surname:"), c);
        c.gridx = 1;
        add(surnameField, c);

        c.gridx = 0; c.gridy = 2;
        add(new JLabel("Password:"), c);
        c.gridx = 1;
        add(passwordField, c);

        JButton loginBtn = new JButton("Login");

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        add(loginBtn, c);

        errorLabel.setForeground(Color.RED);
        c.gridy = 4;
        add(errorLabel, c);

        loginBtn.addActionListener(e -> attemptLogin());
    }

    public void setRole(UserRole role) {
        this.role = role;
        errorLabel.setText(" ");
        nameField.setText("");
        surnameField.setText("");
        passwordField.setText("");
    }

    private void attemptLogin() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String password = new String(passwordField.getPassword());
        boolean success = LoginService.login(role, name, surname, password);

        if (success) {
            frame.showHome(role);
        } else {
            errorLabel.setText("Incorrect name, surname or password");
        }
    }
}
