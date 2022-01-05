package com.carassurance.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.IncidentEntityF;

public class IncidentWithCarF {

    public IncidentEntityF incidents ;


    public CarEntityF car;

    public IncidentWithCarF(IncidentEntityF incidents, CarEntityF car) {
        this.incidents = incidents;
        this.car = car;
    }
}
