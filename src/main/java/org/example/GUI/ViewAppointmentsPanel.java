package org.example.GUI;

import org.example.Entities.Apointment;
import org.example.Entities.Patient;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewAppointmentsPanel extends JPanel {

    private final Patient_home parent;

    private DefaultListModel<Apointment> model;
    private JList<Apointment> list;
    private JTextArea details;

    public ViewAppointmentsPanel(Patient_home parent) {
        this.parent = parent;

        setLayout(new BorderLayout(10,10));

        model = new DefaultListModel<>();
        list = new JList<>(model);
        details = new JTextArea();
        details.setEditable(false);

        add(new JScrollPane(list), BorderLayout.WEST);
        add(new JScrollPane(details), BorderLayout.CENTER);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Apointment a = list.getSelectedValue();
                if (a != null) {
                    details.setText(formatAppointment(a));
                }
            }
        });
    }

    public void loadAppointments() {
        model.clear();

        Patient patient = parent.getPatient();
        if (patient == null) return;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Apointment> apps = session.createQuery(
                            "from Apointment a where a.patient.ID = :pid order by a.date, a.time",
                            Apointment.class)
                    .setParameter("pid", patient.ID)
                    .getResultList();

            for (Apointment a : apps) {
                model.addElement(a);
            }
        }
    }

    private String formatAppointment(Apointment a) {
        return
                "Doctor: " + a.doctor.name + " " + a.doctor.surname + "\n" +
                        "Date: " + a.date + "\n" +
                        "Time: " + a.time + "\n" +
                        "Duration: 30 minutes";
    }
}


