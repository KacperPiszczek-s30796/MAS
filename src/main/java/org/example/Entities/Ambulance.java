package org.example.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Ambulance {
    @Id
    @GeneratedValue
    public int ID;
    public int plateNumber;
    public int capacity;
    public String model;
    @OneToMany(mappedBy = "ambulance", cascade = CascadeType.ALL)
    List<Patient_Ambulance> patientAmbulances;
    @ManyToOne
    public Driver driver;
}
