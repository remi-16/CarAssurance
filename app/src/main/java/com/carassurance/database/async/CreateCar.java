package com.carassurance.database.async;

import android.content.Context;
import android.os.AsyncTask;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.entity.CarEntity;

import com.carassurance.util.OnAsyncEventListener;

public class CreateCar extends AsyncTask<CarEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateCar(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CarEntity... params) {
        try {
            for (CarEntity car : params)
                database.carDao().insert(car);
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
