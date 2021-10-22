package com.carassurance.database.dao;
import android.database.sqlite.SQLiteConstraintException;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.carassurance.database.entity.UserEntity;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<UserEntity> getByEmail(String email);

    @Query("SELECT * FROM users")
    LiveData<List<UserEntity>> getAll();

    @Insert
    void insert(UserEntity user) throws SQLiteConstraintException;

    @Update
    void update(UserEntity user);

    @Delete
    void delete(UserEntity user);

    @Query("DELETE FROM users")
    void deleteAll();
}