package com.carassurance.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.carassurance.BaseApp;
import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.user.CreateUser;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.pojo.CarsWithUser;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;
public class UserRepository {

    private static UserRepository instance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<UserEntity> getUser(final String id, Application application) {
        return ((BaseApp) application).getDatabase().userDao().getById(id);
    }

    public LiveData<List<CarsWithUser>> getAllCarByOwner(final String id, Application application) {
        return ((BaseApp) application).getDatabase().userDao().getAllCarByOwner(id);
    }

    public LiveData<List<UserEntity>> getAllUsers(Application application) {
        return ((BaseApp) application).getDatabase().userDao().getAll();
    }

    public void insert(final UserEntity user, OnAsyncEventListener callback, Application application) {
        new CreateUser(application, callback).execute(user);
    }

}

