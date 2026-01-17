package org.example.Entities;

import org.example.Enums.LicenseType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Driver extends Person {
    @Enumerated(EnumType.STRING)
    public LicenseType licenseType;
    public int licenseNumber;
    public String drivingPermit;
    public Driver(){super();}
    void viewAmbulanceInformation(){}
}
