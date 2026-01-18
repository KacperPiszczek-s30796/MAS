package org.example.GUI;

import org.example.Entities.Apointment;
import org.example.Entities.Doctor;
import org.example.Entities.Patient;
import org.example.Enums.StatePatient;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class DoctorAppointmentsPanel extends JPanel {

    private Doctor_home parent;

    private DefaultListModel<Apointment> model;
    private JList<Apointment> list;

    private JTextArea details;
    private JTextArea observations;

    private JButton btnSaveObs;
    private JButton btnTreated;
    private JButton btnLongTerm;

    private Apointment selected;

    public DoctorAppointmentsPanel(Doctor_home parent) {
        this.parent = parent;

        setLayout(new BorderLayout(10,10));

        model = new DefaultListModel<>();
        list = new JList<>(model);

        details = new JTextArea();
        details.setEditable(false);

        observations = new JTextArea(4,20);

        btnSaveObs = new JButton("Save observations");
        btnTreated = new JButton("Release as Treated");
        btnLongTerm = new JButton("Long-term care");

        JPanel bottom = new JPanel();
        bottom.add(btnSaveObs);
        bottom.add(btnTreated);
        bottom.add(btnLongTerm);

        JPanel center = new JPanel(new GridLayout(2,1));
        center.add(new JScrollPane(details));
        center.add(new JScrollPane(observations));

        add(new JScrollPane(list), BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selected = list.getSelectedValue();
                if (selected != null) {
                    details.setText(format(selected));
                    observations.setText(selected.observations);
                }
            }
        });

        btnSaveObs.addActionListener(e -> saveObservations());
        btnTreated.addActionListener(e -> release(StatePatient.Treated));
        btnLongTerm.addActionListener(e -> release(StatePatient.inLongTermCare));
    }

    public void loadAppointments() {
        model.clear();

        Doctor d = parent.getDoctor();
        if (d == null) return;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Apointment> apps =
                    session.createQuery(
                                    "from Apointment a where a.doctor.ID = :did",
                                    Apointment.class)
                            .setParameter("did", d.ID)
                            .getResultList();

            for (Apointment a : apps) {
                model.addElement(a);
            }
        }
    }

    private void saveObservations() {
        if (selected == null) return;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            selected.observations = observations.getText();
            session.update(selected);
            tx.commit();
        }
    }

    private void release(StatePatient state) {
        if (selected == null) return;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Patient p = selected.patient;
            p.state = state;

            session.update(p);
            tx.commit();
        }

        loadAppointments();
        details.setText("");
        observations.setText("");
    }

    private String format(Apointment a) {
        return
                "Patient: " + a.patient.name + " " + a.patient.surname + "\n" +
                        "Date: " + a.date + "\n" +
                        "Time: " + a.time + "\n" +
                        "Doctor: " + a.doctor.surname;
    }
}

