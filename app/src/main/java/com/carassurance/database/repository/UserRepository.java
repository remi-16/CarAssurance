package com.carassurance.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.user.CreateUser;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;
public class UserRepository {

    private static UserRepository instance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<UserEntity> getUser(final String email, Application application) {
        return AppDatabase.getInstance(application).userDao().getByEmail(email);
    }

    public LiveData<List<UserEntity>> getAllUsers(Application application) {
        return AppDatabase.getInstance(application).userDao().getAll();
    }

    public void insert(final UserEntity user, OnAsyncEventListener callback, Application application) {
        new CreateUser(application, callback).execute(user);
    }

}

