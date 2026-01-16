package org.example.Entities;

import org.example.Enums.StatePatient;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Patient_Ambulance {
    @Id
    @GeneratedValue
    int ID;
    public Date date;
    public Date dateArrivalAtHospital;
    public String locationOfAccident;
    @Enumerated(EnumType.STRING)
    public StatePatient status;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Patient patient;
    @ManyToOne
    public Ambulance ambulance;
}
