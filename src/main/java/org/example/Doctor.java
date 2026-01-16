package org.example;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Doctor extends Person{
    @Enumerated(EnumType.STRING)
    StateDoctor state;
    String specialization;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    List<Apointment> appointments;
    @ManyToOne
    Department department;
    public Doctor() {}
    public Doctor(String name, String middleName, String surname, String nationality, LocalDate dob,
                  StateDoctor state, String specialization) {
        super(name, middleName, surname, nationality, dob);
        this.state = state;
        this.specialization = specialization;
    }
    @Override
    public String toString() {
        return super.toString() + ", Doctor{" +
                "state=" + state +
                ", specialization='" + specialization + '\'' +
                '}';
    }
    void TrackAmbulances(){}
    void ExaminePatient(){}
    void AssessSeverity(){}
    void AdmitOrRelease(){}
}
