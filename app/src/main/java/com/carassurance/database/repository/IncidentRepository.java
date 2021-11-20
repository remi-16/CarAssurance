package com.carassurance.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.carassurance.BaseApp;
import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.car.CreateCar;
import com.carassurance.database.async.car.DeleteCar;
import com.carassurance.database.async.car.UpdateCar;
import com.carassurance.database.async.incident.CreateIncident;
import com.carassurance.database.async.incident.DeleteIncident;
import com.carassurance.database.async.incident.UpdateIncident;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.pojo.CarsWithUser;
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;

public class IncidentRepository {
    private static com.carassurance.database.repository.IncidentRepository instance;

    private IncidentRepository() {
    }

    public static IncidentRepository getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new IncidentRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<IncidentEntity>> getAllIncidentByUser(final String email, Application application) {
        return AppDatabase.getInstance(application).incidentDao().getAllIncidentsByClient(email);
    }

    public LiveData<List<IncidentWithCar>> getAllIncidentWithCar(final String id, Application application) {
        return ((BaseApp) application).getDatabase().incidentDao().getAllIncidentsWithCarByClient(id);
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


    public void update(final IncidentEntity incident, OnAsyncEventListener callback,
                       Application application) {
        new UpdateIncident(application, callback).execute(incident);
    }

    public void delete(final IncidentEntity incident, OnAsyncEventListener callback,
                       Application application) {
        new DeleteIncident(application, callback).execute(incident);
    }
}
