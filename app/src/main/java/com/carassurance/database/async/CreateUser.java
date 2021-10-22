package com.carassurance.database.async;

import android.content.Context;
import android.os.AsyncTask;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.util.OnAsyncEventListener;


public class CreateUser extends AsyncTask<UserEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateUser(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(UserEntity... params) {
        try {
            for (UserEntity client : params)
                database.userDao().insert(client);
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