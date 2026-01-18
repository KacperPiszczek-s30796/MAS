package org.example.GUI;

import org.example.Entities.Doctor;

import javax.swing.*;
import java.awt.*;

public class Doctor_home extends JPanel {

    private Doctor doctor;

    public void setUser(Doctor d) {
        this.doctor = d;
        appointmentsPanel.loadAppointments();
        ambulancesPanel.loadAmbulances();
        allPatientsPanel.loadPatients();  // optional
    }

    private JButton btnAmbulances;
    private JButton btnAppointments;
    private JButton btnAllPatients;

    private CardLayout cardLayout;
    private JPanel centerPanel;

    private DoctorAmbulancesPanel ambulancesPanel;
    private DoctorAppointmentsPanel appointmentsPanel;
    private PanelPatientsAndTheirAppointments allPatientsPanel; // <-- add this

    public Doctor_home() {
        setLayout(new BorderLayout());

        btnAmbulances = new JButton("Ambulances heading to hospital");
        btnAppointments = new JButton("My patients & appointments");
        btnAllPatients = new JButton("All patients info");

        JPanel top = new JPanel();
        top.add(btnAmbulances);
        top.add(btnAppointments);
        top.add(btnAllPatients);

        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        ambulancesPanel = new DoctorAmbulancesPanel();
        appointmentsPanel = new DoctorAppointmentsPanel(this);
        allPatientsPanel = new PanelPatientsAndTheirAppointments(); // <-- create

        centerPanel.add(ambulancesPanel, "AMBULANCES");
        centerPanel.add(appointmentsPanel, "APPOINTMENTS");
        centerPanel.add(allPatientsPanel, "ALL_PATIENTS"); // <-- add

        add(top, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnAmbulances.addActionListener(e -> {
            ambulancesPanel.loadAmbulances();
            cardLayout.show(centerPanel, "AMBULANCES");
        });

        btnAppointments.addActionListener(e -> {
            appointmentsPanel.loadAppointments();
            cardLayout.show(centerPanel, "APPOINTMENTS");
        });

        btnAllPatients.addActionListener(e -> {
            allPatientsPanel.loadPatients();
            cardLayout.show(centerPanel, "ALL_PATIENTS"); // <-- show
        });
    }

    public Doctor getDoctor() {
        return doctor;
    }
}


