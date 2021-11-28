package com.carassurance.database.async.car;

import android.app.Application;
import android.os.AsyncTask;

import com.carassurance.BaseApp;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.util.OnAsyncEventListener;

public class UpdateCar extends AsyncTask<CarEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateCar(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CarEntity... params) {
        try {
            for (CarEntity car : params)
                ((BaseApp) application).getDatabase().carDao()
                        .update(car);
        } catch (Exception e) {
            this.exception = e;
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