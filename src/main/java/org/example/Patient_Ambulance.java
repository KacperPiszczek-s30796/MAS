package org.example;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Patient_Ambulance {
    @Id
    @GeneratedValue
    int ID;
    Date date, dateArrivalAtHospital;
    String locationOfAccident;
    @Enumerated(EnumType.STRING)
    StatePatient status;
    @ManyToOne
    Patient patient;
    @ManyToOne
    Ambulance ambulance;
}
