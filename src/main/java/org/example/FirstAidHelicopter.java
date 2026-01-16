package org.example;

import javax.persistence.Entity;

@Entity
public class FirstAidHelicopter extends Ambulance{
    String supplyDescription;
    int maxAltitude, requiredAreaToLand;
}
