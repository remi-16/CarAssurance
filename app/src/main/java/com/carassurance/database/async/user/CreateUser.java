package com.carassurance.database.async.user;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.carassurance.BaseApp;
import com.carassurance.database.AppDatabase;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.util.OnAsyncEventListener;


public class CreateUser extends AsyncTask<UserEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateUser(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(UserEntity... params) {
        try {
            for (UserEntity user : params)
                ((BaseApp) application).getDatabase().userDao()
                        .insert(user);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}