package com.carassurance.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.pojo.CarsWithUser;

import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM cars")
    LiveData<List<CarEntity>> getAllCar();

    @Query("SELECT * FROM cars Where owner =:owner")
    LiveData<List<CarEntity>> getAllCarByOwner(String owner);

    @Query("SELECT * FROM cars WHERE number_plate = :plate")
    LiveData<CarEntity> getByPlate(String plate);


    @Insert
    void insert(CarEntity car) throws SQLiteConstraintException;

    @Update
    void update(CarEntity car);

    @Delete
    void delete(CarEntity car);

    @Query("DELETE FROM cars")
    void deleteAll();
}
