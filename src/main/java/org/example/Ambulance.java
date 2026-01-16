package org.example;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Ambulance {
    @Id
    @GeneratedValue
    int ID;
    int plateNumber, capacity;
    String model;
    @OneToMany(mappedBy = "ambulance", cascade = CascadeType.ALL)
    List<Patient_Ambulance> patientAmbulances;
}
