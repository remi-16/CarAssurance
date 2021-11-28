package com.carassurance.database.dao;
import android.database.sqlite.SQLiteConstraintException;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.pojo.CarsWithUser;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :id")
    LiveData<UserEntity> getById(String id);

    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> getAll();


    @Transaction
    @Query("SELECT * FROM users WHERE email = :owner")
    LiveData<List<CarsWithUser>> getAllCarByOwner(String owner);

    @Insert
    void insert(UserEntity user) throws SQLiteConstraintException;

    @Update
    void update(UserEntity user);

    @Delete
    void delete(UserEntity user);

    @Query("DELETE FROM users")
    void deleteAll();
}