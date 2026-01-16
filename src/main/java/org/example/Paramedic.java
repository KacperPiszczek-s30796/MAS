package org.example;

import javax.persistence.Entity;

@Entity
public class Paramedic extends Person {
    int CPRNumber, ALSNumber;
    void PickupPatient(){}
    void RecordPatientRefusal(){}
    void CompleteMissingData(){}
    void TransportToHospital(){}
    void ConfirmArrivalOfPatient(){}

}
