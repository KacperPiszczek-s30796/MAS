package org.example.Entities;

import javax.persistence.Entity;

@Entity
public class SurgicalBoat extends Ambulance {
    String equipmentDescription;
    int length, maxSpeed;
    boolean isEquipmentWaterproof;
}
