package com.carassurance.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.UserEntity;

import java.util.List;


/**
 * https://developer.android.com/reference/android/arch/persistence/room/Relation
 */
public class CarsWithUser {
    @Embedded
    public UserEntity user;

    @Relation(parentColumn = "email", entityColumn = "owner", entity = CarEntity.class)
    public List<CarEntity> cars;
}
