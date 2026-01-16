package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class PanelPatientsAndTheirAppointments extends JPanel {
    public PanelPatientsAndTheirAppointments() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Example Names", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Example data (temporary)
        String[] names = {
                "Maria Kowalska",
                "John Smith",
                "Anna Nowak",
                "Peter Johnson",
                "Katarzyna Zielinska",
                "Michael Brown"
        };

        JList<String> nameList = new JList<>(names);
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(nameList);
        add(scrollPane, BorderLayout.CENTER);
    }
}
