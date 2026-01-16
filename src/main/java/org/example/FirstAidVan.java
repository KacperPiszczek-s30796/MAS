package org.example;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class FirstAidVan extends Ambulance{
    String supplyDescription;
    int maxSpeed, cargoVolume;
    @Enumerated(EnumType.STRING)
    Terrain terrain;
}
