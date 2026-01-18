package org.example.GUI;

import org.example.Entities.Ambulance;
import org.example.Entities.Doctor;
import org.example.Entities.Driver;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Driver_home extends JPanel {

    private Driver driver;

    public void setUser(Driver d) {
        this.driver = d;
        loadAmbulances();
    }

    private JList<Ambulance> ambulanceList;
    private DefaultListModel<Ambulance> ambulanceModel;

    private JTextArea detailsArea;
    private DefaultListModel<String> dateModel;
    private JList<String> dateList;

    public Driver_home() {
        setLayout(new BorderLayout());

        ambulanceModel = new DefaultListModel<>();
        ambulanceList = new JList<>(ambulanceModel);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);

        dateModel = new DefaultListModel<>();
        dateList = new JList<>(dateModel);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(ambulanceList),
                new JScrollPane(detailsArea));

        split.setDividerLocation(200);

        add(split, BorderLayout.CENTER);
        add(new JScrollPane(dateList), BorderLayout.SOUTH);

        ambulanceList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Ambulance a = ambulanceList.getSelectedValue();
                if (a != null) {
                    showAmbulanceDetails(a);
                }
            }
        });
    }

    private void loadAmbulances() {
        ambulanceModel.clear();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Ambulance> list = session.createQuery(
                            "from Ambulance a where a.driver.ID = :id", Ambulance.class)
                    .setParameter("id", driver.ID)
                    .getResultList();

            list.forEach(ambulanceModel::addElement);
        }
    }

    private void showAmbulanceDetails(Ambulance a) {
        detailsArea.setText(a.toString());

        dateModel.clear();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Date> dates = session.createQuery(
                            "select pa.date from Patient_Ambulance pa where pa.ambulance.ID = :id",
                            Date.class)
                    .setParameter("id", a.ID)
                    .getResultList();

            dates.forEach(d -> dateModel.addElement(
                    d.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                            .toString()
            ));
        }
    }

}

