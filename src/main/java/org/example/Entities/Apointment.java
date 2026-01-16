package org.example.Entities;

import org.example.Enums.StatusAppointment;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Apointment {
    @Id
    @GeneratedValue
    int ID;
    public Date date;
    public LocalTime time;
    public LocalTime duration;
    @Enumerated(EnumType.STRING)
    public StatusAppointment status;
    public String observations;
    @ManyToOne
    public Patient patient;
    @ManyToOne
    public Doctor doctor;
}
