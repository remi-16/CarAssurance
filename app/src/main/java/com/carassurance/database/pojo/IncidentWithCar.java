package com.carassurance.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.IncidentEntity;

import java.util.List;
import java.util.Objects;

public class IncidentWithCar {
    @Embedded
    public IncidentEntity incidents ;

    @Relation(parentColumn = "car_id", entityColumn = "id", entity = CarEntity.class)
    public CarEntity car;


}
