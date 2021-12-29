package com.carassurance.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.entity.UserEntityF;

import java.util.List;


/**
 * https://developer.android.com/reference/android/arch/persistence/room/Relation
 */
public class CarsWithUserF {

    public UserEntityF user;

    public List<CarEntityF> cars;
}
