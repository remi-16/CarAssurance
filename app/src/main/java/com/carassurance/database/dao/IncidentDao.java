package com.carassurance.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.IncidentEntity;

import java.util.List;

@Dao
public interface IncidentDao {
    @Query("SELECT * FROM incidents")
    LiveData<List<IncidentEntity>> getAllIncidents();

    @Query("SELECT * FROM incidents WHERE client = :emailClient")
    LiveData<List<IncidentEntity>> getAllIncidentsByClient(String emailClient);

    @Query("SELECT * FROM incidents WHERE id = :id")
    LiveData<IncidentEntity> getById(Long id);

    @Insert
    void insert(IncidentEntity incident) throws SQLiteConstraintException;

    @Update
    void update(IncidentEntity incident);

    @Delete
    void delete(IncidentEntity incident);

    @Query("DELETE FROM incidents")
    void deleteAll();
}
