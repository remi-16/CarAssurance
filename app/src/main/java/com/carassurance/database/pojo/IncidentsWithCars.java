package com.carassurance.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.IncidentEntity;


import java.util.List;

public class IncidentsWithCars {
    @Embedded
    public CarEntity car;

    @Relation(parentColumn = "id", entityColumn = "car_id", entity = IncidentEntity.class)
    public List<IncidentEntity> incidents;
}
