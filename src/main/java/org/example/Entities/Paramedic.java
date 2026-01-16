package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class Paramedic extends Person {
    public int CPRNumber;
    public int ALSNumber;
    void PickupPatient(){}
    void RecordPatientRefusal(){}
    void CompleteMissingData(){}
    void TransportToHospital(){}
    void ConfirmArrivalOfPatient(){}

}
