package org.example;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class SurgicalVan extends Ambulance{
    String equipmentDescription;
    int maxSpeed, cargoVolume;
    @Enumerated(EnumType.STRING)
    Terrain terrain;
}
