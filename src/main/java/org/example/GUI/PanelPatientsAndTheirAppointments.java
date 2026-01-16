package org.example.GUI;

import org.example.Entities.Apointment;
import org.example.Entities.Patient;
import org.example.Utilities.PatientRepository;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PanelPatientsAndTheirAppointments extends JPanel {
    public PanelPatientsAndTheirAppointments() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Example Names", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        List<Patient> patients = PatientRepository.findAllWithAppointments();
        JList<Patient> list = new JList<>(patients.toArray(new Patient[0]));
        JList<Apointment> appointmentList = new JList<>();

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Patient selected = list.getSelectedValue();
                if (selected != null) {
                    List<Apointment> apps = selected.getAppointments();
                    appointmentList.setListData(
                            apps.toArray(new Apointment[0])
                    );
                }
            }
        });


        JScrollPane patientScroll = new JScrollPane(list);
        JScrollPane appointmentScroll = new JScrollPane(appointmentList);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(patientScroll);
        centerPanel.add(appointmentScroll);

        add(centerPanel, BorderLayout.CENTER);
    }
}
