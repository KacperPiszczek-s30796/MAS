package org.example;

import javax.persistence.Entity;

@Entity
public class SurgicalBoat extends Ambulance{
    String equipmentDescription;
    int length, maxSpeed;
    boolean isEquipmentWaterproof;
}
