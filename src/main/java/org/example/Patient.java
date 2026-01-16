package org.example;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends Person{
    String sex;
    @Enumerated(EnumType.STRING)
    StatePatient state;
    float weight, height;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<Apointment> appointments;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<Patient_Ambulance> patientAmbulances;
    void BookAppointment(){}
    void ViewAppointment(){}
    void ConfirmTreatment(){}
}
