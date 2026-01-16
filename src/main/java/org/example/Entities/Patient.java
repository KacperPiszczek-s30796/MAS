package org.example.Entities;

import org.example.Enums.StatePatient;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends Person {
    public String sex;
    @Enumerated(EnumType.STRING)
    public StatePatient state;
    public float weight;
    public float height;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<Apointment> appointments;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<Patient_Ambulance> patientAmbulances;
    public List<Apointment> getAppointments(){
        return appointments;
    }
    @Override
    public String toString() {
        return "Person{" +
                "id=" + ID +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", nationality='" + nationality + '\'' +
                ", age=" + age() +
                ", weight=" + weight +
                ", height=" + height +
                ", state=" + state +
                '}';
    }
    void BookAppointment(){}
    void ViewAppointment(){}
    void ConfirmTreatment(){}
}
