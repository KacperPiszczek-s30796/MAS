package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class FirstAidBoat extends Ambulance {
    String supplyDescription;
    int length, maxSpeed;
    boolean isEquipmentWaterproof;
}
