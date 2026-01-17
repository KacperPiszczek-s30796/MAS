package org.example.GUI;

import org.example.Entities.*;
import org.example.Enums.LicenseType;
import org.example.Enums.UserRole;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RegisterPanel extends JPanel {

    private final MainFrame frame;
    private final UserRole role;

    private JTextField tfName;
    private JTextField tfMiddleName;
    private JTextField tfSurname;
    private JTextField tfNationality;
    private JTextField tfDateOfBirth;
    private JPasswordField pfPassword;

    private JPanel roleSpecificPanel;
    private CardLayout cardLayout;

    private JComboBox<String> cbSex;
    private JTextField tfWeight;
    private JTextField tfHeight;

    private JTextField tfSpecialization;

    private JComboBox<LicenseType> cbLicenseType;
    private JTextField tfLicenseNumber;
    private JTextField tfDrivingPermit;

    private JTextField tfCPRNumber;
    private JTextField tfALSNumber;

    private JLabel errorLabel;

    public RegisterPanel(MainFrame frame, UserRole role) {
        this.frame = frame;
        this.role = role;

        setLayout(new BorderLayout(10, 10));
        add(createForm(), BorderLayout.CENTER);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        tfName = addField(panel, c, "Name:", y++);
        tfMiddleName = addField(panel, c, "Middle name:", y++);
        tfSurname = addField(panel, c, "Surname:", y++);
        tfNationality = addField(panel, c, "Nationality:", y++);
        tfDateOfBirth = addField(panel, c, "Date of birth (yyyy-MM-dd):", y++);

        c.gridx = 0; c.gridy = y;
        panel.add(new JLabel("Password:"), c);
        pfPassword = new JPasswordField(15);
        c.gridx = 1;
        panel.add(pfPassword, c);
        y++;

        cardLayout = new CardLayout();
        roleSpecificPanel = new JPanel(cardLayout);

        roleSpecificPanel.add(createPatientPanel(), UserRole.PATIENT.name());
        roleSpecificPanel.add(createDoctorPanel(), UserRole.DOCTOR.name());
        roleSpecificPanel.add(createDriverPanel(), UserRole.DRIVER.name());
        roleSpecificPanel.add(createParamedicPanel(), UserRole.PARAMEDIC.name());

        c.gridx = 0;
        c.gridy = y;
        c.gridwidth = 2;
        panel.add(roleSpecificPanel, c);
        y++;

        cardLayout.show(roleSpecificPanel, role.name());

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(e -> register());

        c.gridy = y;
        panel.add(btnRegister, c);
        y++;

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        c.gridy = y;
        panel.add(errorLabel, c);

        return panel;
    }

    private JTextField addField(JPanel panel, GridBagConstraints c, String label, int y) {
        c.gridx = 0;
        c.gridy = y;
        panel.add(new JLabel(label), c);

        JTextField tf = new JTextField(15);
        c.gridx = 1;
        panel.add(tf, c);
        return tf;
    }


    private JPanel createPatientPanel() {
        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        cbSex = new JComboBox<>(new String[]{"MALE", "FEMALE"});
        tfWeight = new JTextField();
        tfHeight = new JTextField();

        p.add(new JLabel("Sex:"));
        p.add(cbSex);
        p.add(new JLabel("Weight:"));
        p.add(tfWeight);
        p.add(new JLabel("Height:"));
        p.add(tfHeight);
        return p;
    }

    private JPanel createDoctorPanel() {
        JPanel p = new JPanel(new GridLayout(1, 2, 5, 5));
        tfSpecialization = new JTextField();
        p.add(new JLabel("Specialization:"));
        p.add(tfSpecialization);
        return p;
    }

    private JPanel createDriverPanel() {
        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        cbLicenseType = new JComboBox<>(LicenseType.values());
        tfLicenseNumber = new JTextField();
        tfDrivingPermit = new JTextField();

        p.add(new JLabel("License type:"));
        p.add(cbLicenseType);
        p.add(new JLabel("License number:"));
        p.add(tfLicenseNumber);
        p.add(new JLabel("Driving permit:"));
        p.add(tfDrivingPermit);
        return p;
    }

    private JPanel createParamedicPanel() {
        JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));
        tfCPRNumber = new JTextField();
        tfALSNumber = new JTextField();

        p.add(new JLabel("CPR number:"));
        p.add(tfCPRNumber);
        p.add(new JLabel("ALS number:"));
        p.add(tfALSNumber);
        return p;
    }


    private void register() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Person person;

            if (role == UserRole.PATIENT) {
                Patient p = new Patient();
                p.sex = cbSex.getSelectedItem().toString();
                p.weight = Float.parseFloat(tfWeight.getText());
                p.height = Float.parseFloat(tfHeight.getText());
                person = p;

            } else if (role == UserRole.DOCTOR) {
                Doctor d = new Doctor();
                d.specialization = tfSpecialization.getText();
                person = d;

            } else if (role == UserRole.DRIVER) {
                Driver d = new Driver();
                d.licenseType = (LicenseType) cbLicenseType.getSelectedItem();
                d.licenseNumber = Integer.parseInt(tfLicenseNumber.getText());
                d.drivingPermit = tfDrivingPermit.getText();
                person = d;

            } else {
                Paramedic p = new Paramedic();
                p.CPRNumber = Integer.parseInt(tfCPRNumber.getText());
                p.ALSNumber = Integer.parseInt(tfALSNumber.getText());
                person = p;
            }

            person.name = tfName.getText();
            person.middleName = tfMiddleName.getText();
            person.surname = tfSurname.getText();
            person.nationality = tfNationality.getText();
            person.password = new String(pfPassword.getPassword());
            person.dateOfBirth = LocalDate.parse(tfDateOfBirth.getText());

            session.persist(person);
            tx.commit();

            frame.showHome(role, person);

        } catch (Exception ex) {
            errorLabel.setText("Registration failed: " + ex.getMessage());
        }
    }
}
