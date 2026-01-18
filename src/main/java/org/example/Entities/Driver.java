package org.example.Entities;

import org.example.Enums.LicenseType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Driver extends Person {
    @Enumerated(EnumType.STRING)
    public LicenseType licenseType;
    public int licenseNumber;
    public String drivingPermit;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    List<Ambulance> Ambulances;
    public Driver(){super();}
    void viewAmbulanceInformation(){}
}
