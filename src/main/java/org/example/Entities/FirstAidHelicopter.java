package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class FirstAidHelicopter extends Ambulance {
    String supplyDescription;
    int maxAltitude, requiredAreaToLand;
}
