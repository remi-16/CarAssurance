package com.carassurance.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.CreateUser;
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

    public LiveData<UserEntity> getUser(final String email, Context context) {
        return AppDatabase.getInstance(context).userDao().getByEmail(email);
    }

    public LiveData<List<UserEntity>> getAllClients(Context context) {
        return AppDatabase.getInstance(context).userDao().getAll();
    }

    public void insert(final UserEntity client, OnAsyncEventListener callback, Context context) {
        new CreateUser(context, callback).execute(client);
    }

}

