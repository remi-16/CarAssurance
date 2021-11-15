package com.carassurance.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.car.CreateCar;
import com.carassurance.database.async.incident.CreateIncident;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;

public class IncidentRepository {
    private static com.carassurance.database.repository.IncidentRepository instance;

    private IncidentRepository() {
    }

    public static com.carassurance.database.repository.IncidentRepository getInstance() {
        if (instance == null) {
            synchronized (com.carassurance.database.repository.IncidentRepository.class) {
                if (instance == null) {
                    instance = new com.carassurance.database.repository.IncidentRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<IncidentEntity>> getAllIncidentByUser(final String email, Application application) {
        return AppDatabase.getInstance(application).incidentDao().getAllIncidentsByClient(email);
    }

    public LiveData<List<IncidentEntity>> getAllIncident(Application application) {
        return AppDatabase.getInstance(application).incidentDao().getAllIncidents();
    }

    public LiveData<IncidentEntity> getIncidentById(final Long id, Application application) {
        return AppDatabase.getInstance(application).incidentDao().getById(id);
    }

    public void insert(final IncidentEntity incident, OnAsyncEventListener callback, Application application) {
        new CreateIncident(application, callback).execute(incident);
    }
}
