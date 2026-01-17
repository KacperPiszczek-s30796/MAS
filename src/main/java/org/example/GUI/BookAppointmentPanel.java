package org.example.GUI;

import org.example.Entities.Apointment;
import org.example.Entities.Doctor;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class BookAppointmentPanel extends JPanel {

    private final Patient_home parent;

    private JTextField dateField;
    private JTextField timeField;
    private JButton btnConfirm;

    public BookAppointmentPanel(Patient_home parent) {
        this.parent = parent;

        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Date (yyyy-MM-dd):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Time (HH:mm):"));
        timeField = new JTextField();
        add(timeField);

        btnConfirm = new JButton("Confirm booking");
        add(new JLabel());
        add(btnConfirm);

        btnConfirm.addActionListener(e -> book());
    }

    private void book() {
        try {
            LocalDate date = LocalDate.parse(dateField.getText());
            LocalTime time = LocalTime.parse(timeField.getText());

            assignDoctorAndSave(date, time);

            JOptionPane.showMessageDialog(this, "Appointment booked successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date or time format");
        }
    }

    private void assignDoctorAndSave(LocalDate date, LocalTime time) {

        LocalTime newStart = time;
        LocalTime newEnd = time.plusMinutes(30);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            List<Apointment> sameDayAppointments =
                    session.createQuery(
                                    "from Apointment a where a.date = :d",
                                    Apointment.class)
                            .setParameter("d", java.sql.Date.valueOf(date))
                            .getResultList();

            Set<Doctor> busyDoctors = new HashSet<>();

            for (Apointment a : sameDayAppointments) {
                LocalTime existingStart = a.time;
                LocalTime existingEnd = a.time.plusMinutes(30);

                boolean overlaps =
                        existingStart.isBefore(newEnd) &&
                                newStart.isBefore(existingEnd);

                if (overlaps) {
                    busyDoctors.add(a.doctor);
                }
            }

            List<Doctor> allDoctors =
                    session.createQuery("from Doctor", Doctor.class)
                            .getResultList();

            Doctor selected = null;
            long minCount = Long.MAX_VALUE;

            for (Doctor d : allDoctors) {
                if (busyDoctors.contains(d)) continue;

                Long count = session.createQuery(
                                "select count(a) from Apointment a where a.doctor = :doc",
                                Long.class)
                        .setParameter("doc", d)
                        .uniqueResult();

                if (count < minCount) {
                    minCount = count;
                    selected = d;
                }
            }

            if (selected == null) {
                JOptionPane.showMessageDialog(this, "No available doctors");
                tx.rollback();
                return;
            }

            Apointment a = new Apointment();
            a.patient = parent.getPatient();
            a.doctor = selected;
            a.date = java.sql.Date.valueOf(date);
            a.time = time;
            a.duration = LocalTime.of(0, 30);

            session.persist(a);
            tx.commit();
        }
    }
}
