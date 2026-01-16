package org.example;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Driver extends Person{
    @Enumerated(EnumType.STRING)
    LicenseType licenseType;
    int licenseNumber;
    String drivingPermit;
    void viewAmbulanceInformation(){}
}
