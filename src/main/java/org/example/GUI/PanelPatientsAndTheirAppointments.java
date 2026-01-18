package org.example.GUI;

import org.example.Entities.Apointment;
import org.example.Entities.Patient;
import org.example.Utilities.HibernateUtil;
import org.example.Utilities.PatientRepository;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PanelPatientsAndTheirAppointments extends JPanel {

    private DefaultListModel<Patient> patientModel;
    private JList<Patient> patientList;

    private DefaultListModel<Apointment> appointmentModel;
    private JList<Apointment> appointmentList;

    private JTextArea details;

    public PanelPatientsAndTheirAppointments() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("All Patients and Appointments", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        patientModel = new DefaultListModel<>();
        patientList = new JList<>(patientModel);

        appointmentModel = new DefaultListModel<>();
        appointmentList = new JList<>(appointmentModel);

        details = new JTextArea();
        details.setEditable(false);

        // Load data once (admin view)
        loadPatients();

        patientList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadAppointmentsForSelectedPatient();
            }
        });

        appointmentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showPatientAndAppointmentDetails();
            }
        });

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(new JScrollPane(patientList));
        center.add(new JScrollPane(appointmentList));

        add(center, BorderLayout.CENTER);
        add(new JScrollPane(details), BorderLayout.SOUTH);
    }

    public void loadPatients() {
        patientModel.clear();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Patient> patients =
                    session.createQuery("from Patient", Patient.class)
                            .getResultList();

            for (Patient p : patients) {
                patientModel.addElement(p);
            }
        }
    }

    private void loadAppointmentsForSelectedPatient() {
        appointmentModel.clear();
        details.setText("");

        Patient selected = patientList.getSelectedValue();
        if (selected == null) return;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Apointment> apps =
                    session.createQuery("from Apointment a where a.patient = :p", Apointment.class)
                            .setParameter("p", selected)
                            .getResultList();

            for (Apointment a : apps) {
                appointmentModel.addElement(a);
            }
        }
    }

    private void showPatientAndAppointmentDetails() {
        Patient selectedPatient = patientList.getSelectedValue();
        Apointment selectedAppointment = appointmentList.getSelectedValue();

        if (selectedPatient == null || selectedAppointment == null) {
            details.setText("");
            return;
        }

        String text =
                "=== PATIENT INFO ===\n" +
                        selectedPatient.toString() +
                        "\n\n=== APPOINTMENT INFO ===\n" +
                        selectedAppointment.toString();

        details.setText(text);
    }
}

