package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class SurgicalBoat extends Ambulance {
    public String equipmentDescription;
    public int length, maxSpeed;
    public boolean isEquipmentWaterproof;
}
