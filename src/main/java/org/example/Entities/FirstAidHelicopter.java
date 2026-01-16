package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class FirstAidHelicopter extends Ambulance {
    public String supplyDescription;
    public int maxAltitude, requiredAreaToLand;
}
