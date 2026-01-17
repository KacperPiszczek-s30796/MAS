package org.example.GUI;

import org.example.Entities.Doctor;
import org.example.Entities.Patient;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;

public class Patient_home extends JPanel {

    private Patient patient;

    public void setUser(Patient p) {
        this.patient = p;
        loadAppointments();
    }

    private JButton btnBook;
    private JButton btnView;

    private JPanel centerPanel;
    private CardLayout cardLayout;

    private BookAppointmentPanel bookPanel;
    private ViewAppointmentsPanel viewPanel;

    public Patient_home() {
        setLayout(new BorderLayout());

        btnBook = new JButton("Book appointment");
        btnView = new JButton("View appointments");

        JPanel top = new JPanel();
        top.add(btnBook);
        top.add(btnView);

        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        bookPanel = new BookAppointmentPanel(this);
        viewPanel = new ViewAppointmentsPanel(this);

        centerPanel.add(bookPanel, "BOOK");
        centerPanel.add(viewPanel, "VIEW");

        add(top, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnBook.addActionListener(e -> cardLayout.show(centerPanel, "BOOK"));
        btnView.addActionListener(e -> {
            viewPanel.loadAppointments();
            cardLayout.show(centerPanel, "VIEW");
        });
    }

    public Patient getPatient() {
        return patient;
    }
    private void loadAppointments() {
        if (patient == null) return;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            patient = session.get(Patient.class, patient.ID);
        }

        if (viewPanel != null) {
            viewPanel.loadAppointments();
        }
    }


}

