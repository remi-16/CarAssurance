package com.carassurance.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.carassurance.BaseApp;
import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.incident.CreateIncident;
import com.carassurance.database.async.incident.DeleteIncident;
import com.carassurance.database.async.incident.UpdateIncident;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.IncidentEntityF;
import com.carassurance.database.firebase.IncidentLiveData;
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class IncidentRepositoryF {
    private static final String TAG = "IncidentRepositoryF";
    private static IncidentRepositoryF instance;

    private IncidentRepositoryF() {
    }

    public static IncidentRepositoryF getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new IncidentRepositoryF();
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
    public LiveData<IncidentEntityF> getIncidentById(final String incidentId, final String carId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("cars")
                .child(carId)
                .child("incidents")
                .child(incidentId);
        return new IncidentLiveData(reference);
    }

    public void insert(final IncidentEntityF incident, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("cars")
                .child(incident.getCar_id())
                .child("incidents");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("cars")
                .child(incident.getCar_id())
                .child("incidents")
                .child(key)
                .setValue(incident, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }


    public void update(final IncidentEntityF incident, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("cars")
                .child(incident.getCar_id())
                .child("incidents")
                .child(incident.getId())
                .updateChildren(incident.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final IncidentEntityF incident, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("cars")
                .child(incident.getCar_id())
                .child("incidents")
                .child(incident.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
