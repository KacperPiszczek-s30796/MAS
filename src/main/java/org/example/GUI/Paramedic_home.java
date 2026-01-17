package org.example.GUI;

import org.example.Entities.Doctor;
import org.example.Entities.Paramedic;
import org.example.Entities.Patient;
import org.example.Entities.Patient_Ambulance;
import org.example.Enums.StatePatient;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Paramedic_home extends JPanel {
    private Paramedic paramedic;
    public void setUser(Paramedic p) {
        this.paramedic = p;
    }
    private JList<Patient> patientList;
    private DefaultListModel<Patient> patientModel;

    private JTextArea patientInfo;
    private JTextArea ambulanceInfo;

    private JButton btnArrived;
    private JButton btnPickup;
    private JButton btnRefused;
    private JButton btnContinue;

    private Patient selectedPatient;
    private Patient_Ambulance selectedTransport;

    private boolean updatingList = false;

    public Paramedic_home() {
        setLayout(new BorderLayout());

        patientModel = new DefaultListModel<>();
        patientList = new JList<>(patientModel);

        patientInfo = new JTextArea();
        ambulanceInfo = new JTextArea();

        btnArrived = new JButton("Confirm arrival at pickup location");
        btnPickup = new JButton("Confirm patient pickup");
        btnRefused = new JButton("Patient refused transport");
        btnContinue = new JButton("Continue transport");

        JPanel rightPanel = new JPanel(new GridLayout(4,1,10,10));
        rightPanel.add(btnArrived);
        rightPanel.add(btnPickup);
        rightPanel.add(btnRefused);
        rightPanel.add(btnContinue);

        JPanel infoPanel = new JPanel(new GridLayout(2,1));
        infoPanel.add(new JScrollPane(patientInfo));
        infoPanel.add(new JScrollPane(ambulanceInfo));

        add(new JScrollPane(patientList), BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.SOUTH);

        loadNeedingHelpPatients();

        patientList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            if (updatingList) return; // prevent clearing selection during reload

            Patient p = patientList.getSelectedValue();
            if (p != null) {
                selectedPatient = p;
                loadSelectedPatientData();
            }
        });


        btnArrived.addActionListener(e -> confirmArrival());
        btnPickup.addActionListener(e -> confirmPickup());
        btnRefused.addActionListener(e -> markRefused());
        btnContinue.addActionListener(e -> continueTransport());
    }

    private void loadNeedingHelpPatients() {
        updatingList = true;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Patient> list = session.createQuery(
                            "from Patient p where p.state = :st", Patient.class)
                    .setParameter("st", StatePatient.NeedingHelp)
                    .getResultList();

            patientModel.clear();
            list.forEach(patientModel::addElement);

            // if selected patient still exists in list -> keep it selected
            if (selectedPatient != null) {
                for (int i = 0; i < patientModel.size(); i++) {
                    if (patientModel.get(i).ID == selectedPatient.ID) {
                        patientList.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } finally {
            updatingList = false;
        }
    }


    private void loadSelectedPatientData() {
        if (selectedPatient == null) return;

        // fetch transport assignment
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            selectedTransport = session.createQuery(
                            "from Patient_Ambulance pa where pa.patient.ID = :id",
                            Patient_Ambulance.class)
                    .setParameter("id", selectedPatient.ID)
                    .uniqueResult();
        }

        patientInfo.setText(selectedPatient.toString());

        if (selectedTransport != null) {
            ambulanceInfo.setText(selectedTransport.ambulance.toString());
        } else {
            ambulanceInfo.setText("No ambulance assigned.");
        }
    }

    private void confirmArrival() {
        if (selectedTransport == null) return;
        updateTransportState(StatePatient.PickedUp, "Arrival confirmed.");
    }

    private void confirmPickup() {
        if (selectedTransport == null) return;
        updateTransportState(StatePatient.HeadingToHospital, "Pickup confirmed.");
    }

    private void markRefused() {
        if (selectedPatient == null) return;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            selectedPatient.state = StatePatient.Delivered; // or a separate "Refused" state
            session.update(selectedPatient);

            tx.commit();
        }
        loadNeedingHelpPatients();
        clearInfo();
    }

    private void continueTransport() {
        if (selectedPatient == null) return;

        if (selectedPatient.weight == 0 || selectedPatient.height == 0 || selectedPatient.sex == null) {
            JOptionPane.showMessageDialog(this, "Missing patient data. Please update.");
            // open update panel (same as register but update fields)
            new UpdatePatientDialog(selectedPatient).setVisible(true);
        }

        updateTransportState(StatePatient.Delivered, "Transport completed.");
        loadNeedingHelpPatients();
        clearInfo();
    }

    private void updateTransportState(StatePatient state, String message) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            selectedPatient.state = state;
            session.update(selectedPatient);

            tx.commit();
        }
        JOptionPane.showMessageDialog(this, message);
        loadNeedingHelpPatients();
    }

    private void clearInfo() {
        patientInfo.setText("");
        ambulanceInfo.setText("");
        selectedPatient = null;
        selectedTransport = null;
    }
}
