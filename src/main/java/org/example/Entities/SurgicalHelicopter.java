package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class SurgicalHelicopter extends Ambulance {
    public String equipmentDescription;
    public int maxAltitude, requiredAreaToLand;
}
