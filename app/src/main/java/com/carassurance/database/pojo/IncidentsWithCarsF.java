package com.carassurance.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.IncidentEntityF;

import java.util.List;

public class IncidentsWithCarsF {
    @Embedded
    public CarEntityF car;


    public List<IncidentEntityF> incidents;
}
