package com.carassurance.database.repository;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.IncidentEntityF;
import com.carassurance.database.firebase.CarIncidentsListLiveData;
import com.carassurance.database.firebase.CarListLiveData;
import com.carassurance.database.firebase.IncidentLiveData;
import com.carassurance.database.firebase.IncidentsListLiveData;
import com.carassurance.database.firebase.UserIncidentsListLiveData;
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.database.pojo.IncidentWithCarF;
import com.carassurance.database.pojo.IncidentsWithCars;
import com.carassurance.database.pojo.IncidentsWithCarsF;
import com.carassurance.database.pojo.IncidentsWithUserF;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IncidentRepositoryF {
    private static final String TAG = "IncidentRepositoryF";
    private static IncidentRepositoryF instance;
    private CarRepositoryF carRepositoryF;

    private IncidentRepositoryF() {
        carRepositoryF = new CarRepositoryF();
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




    public LiveData<List<IncidentWithCarF>> getAllIncidentWithCar(final String owner) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(owner)
                .child("cars");
        return new IncidentsListLiveData(reference, owner);
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
