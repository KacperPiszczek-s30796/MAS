package org.example.GUI;

import org.example.Entities.Patient;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

public class UpdatePatientDialog extends JDialog {

    private JTextField tfSex, tfWeight, tfHeight;
    private JButton save;

    private Patient patient;

    public UpdatePatientDialog(Patient patient) {
        this.patient = patient;

        setLayout(new GridLayout(4,2));
        setSize(300,200);

        tfSex = new JTextField(patient.sex);
        tfWeight = new JTextField(String.valueOf(patient.weight));
        tfHeight = new JTextField(String.valueOf(patient.height));

        add(new JLabel("Sex"));
        add(tfSex);
        add(new JLabel("Weight"));
        add(tfWeight);
        add(new JLabel("Height"));
        add(tfHeight);

        save = new JButton("Save");
        add(save);

        save.addActionListener(e -> {
            patient.sex = tfSex.getText();
            patient.weight = Float.parseFloat(tfWeight.getText());
            patient.height = Float.parseFloat(tfHeight.getText());

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                session.update(patient);
                tx.commit();
            }

            dispose();
        });
    }
}
