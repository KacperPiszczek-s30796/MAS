package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class FirstAidBoat extends Ambulance {
    public String supplyDescription;
    public int length, maxSpeed;
    public boolean isEquipmentWaterproof;
}
