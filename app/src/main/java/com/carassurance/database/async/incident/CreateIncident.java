package com.carassurance.database.async.incident;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.carassurance.BaseApp;
import com.carassurance.database.AppDatabase;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.util.OnAsyncEventListener;

public class CreateIncident extends AsyncTask<IncidentEntity, Void, Void> {

        private Application application;
        private OnAsyncEventListener callback;
        private Exception exception;

        public CreateIncident(Application application, OnAsyncEventListener callback) {
            this.application = application;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(IncidentEntity... params) {
            try {
                for (IncidentEntity incident : params)
                    ((BaseApp) application).getDatabase().incidentDao()
                            .insert(incident);
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

