package org.example;

import javax.persistence.Entity;

@Entity
public class SurgicalHelicopter extends Ambulance{
    String equipmentDescription;
    int maxAltitude, requiredAreaToLand;
}
