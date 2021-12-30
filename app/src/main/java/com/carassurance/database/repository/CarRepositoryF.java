package com.carassurance.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.car.CreateCar;
import com.carassurance.database.async.car.DeleteCar;
import com.carassurance.database.async.car.UpdateCar;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.IncidentEntityF;
import com.carassurance.database.firebase.CarListLiveData;
import com.carassurance.database.firebase.CarLiveData;
import com.carassurance.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CarRepositoryF {

        private static final String TAG = "CarRepositoryF";
        private static CarRepositoryF instance;

        private CarRepositoryF() {
        }

        public static CarRepositoryF getInstance() {
            if (instance == null) {
                synchronized (CarRepositoryF.class) {
                    if (instance == null) {
                        instance = new CarRepositoryF();
                    }
                }
            }
            return instance;
        }

        public LiveData<CarEntityF> getCar(final String carId) {
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("cars")
                    .child(carId);
            return new CarLiveData(reference);
        }

        public LiveData<List<CarEntityF>> getAllCarByOwner(final String owner) {
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(owner)
                    .child("cars");
            return new CarListLiveData(reference, owner);
        }


        public void insert(final CarEntityF car, final OnAsyncEventListener callback) {
            DatabaseReference reference = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(car.getOwner())
                    .child("cars");
            String key = reference.push().getKey();
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(car.getOwner())
                    .child("cars")
                    .child(key)
                    .setValue(car, (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }


        public void update(final CarEntityF car, OnAsyncEventListener callback) {
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(car.getOwner())
                    .child("cars")
                    .child(car.getId())
                    .updateChildren(car.toMap(), (databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }


        public void delete(final CarEntityF car, OnAsyncEventListener callback) {
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(car.getOwner())
                    .child("cars")
                    .child(car.getId())
                    .removeValue((databaseError, databaseReference) -> {
                        if (databaseError != null) {
                            callback.onFailure(databaseError.toException());
                        } else {
                            callback.onSuccess();
                        }
                    });
        }


    }



