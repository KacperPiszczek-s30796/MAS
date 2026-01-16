package org.example;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Apointment {
    @Id
    @GeneratedValue
    int ID;
    Date date;
    LocalTime time, duration;
    @Enumerated(EnumType.STRING)
    StatusAppointment status;
    String observations;
    @ManyToOne
    Patient patient;
    @ManyToOne
    Doctor doctor;
}
