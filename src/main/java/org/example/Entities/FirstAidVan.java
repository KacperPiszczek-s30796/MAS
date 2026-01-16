package org.example.Entities;

import org.example.Enums.Terrain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class FirstAidVan extends Ambulance {
    String supplyDescription;
    int maxSpeed, cargoVolume;
    @Enumerated(EnumType.STRING)
    Terrain terrain;
}
