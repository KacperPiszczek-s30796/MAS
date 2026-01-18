package org.example.GUI;

import org.example.Entities.Patient_Ambulance;
import org.example.Enums.StatePatient;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

class DoctorAmbulancesPanel extends JPanel {

    private DefaultListModel<Patient_Ambulance> model;
    private JList<Patient_Ambulance> list;
    private JTextArea details;

    public DoctorAmbulancesPanel() {
        setLayout(new BorderLayout(10,10));

        model = new DefaultListModel<>();
        list = new JList<>(model);
        details = new JTextArea();
        details.setEditable(false);

        add(new JScrollPane(list), BorderLayout.WEST);
        add(new JScrollPane(details), BorderLayout.CENTER);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Patient_Ambulance pa = list.getSelectedValue();
                if (pa != null) {
                    details.setText(format(pa));
                }
            }
        });
    }

    public void loadAmbulances() {
        model.clear();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Patient_Ambulance> listDb =
                    session.createQuery(
                                    "from Patient_Ambulance pa where pa.status in (:statuses)",
                                    Patient_Ambulance.class)
                            .setParameter("statuses", Arrays.asList(
                                    StatePatient.NeedingHelp,
                                    StatePatient.PickedUp,
                                    StatePatient.HeadingToHospital
                            ))
                            .getResultList();

            for (Patient_Ambulance pa : listDb) {
                model.addElement(pa);
            }
        }
    }


    private String format(Patient_Ambulance pa) {
        return
                "Patient: " + pa.patient.name + " " + pa.patient.surname + "\n" +
                        "Ambulance: " + pa.ambulance + "\n" +
                        "Status: " + pa.status + "\n" +
                        "Accident location: " + pa.locationOfAccident;
    }
}


