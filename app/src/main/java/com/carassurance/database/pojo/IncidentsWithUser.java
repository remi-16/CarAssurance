package com.carassurance.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.UserEntity;

import java.util.List;

/**
 * https://developer.android.com/reference/android/arch/persistence/room/Relation
 */
public class IncidentsWithUser {
    @Embedded
    public UserEntity user;

    @Relation(parentColumn = "email", entityColumn = "client", entity = IncidentEntity.class)
    public List<IncidentEntity> incidents;
}
