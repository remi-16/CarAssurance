package com.carassurance.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.car.CreateCar;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.pojo.CarsWithUser;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;

public class CarRepository {


        private static com.carassurance.database.repository.CarRepository instance;

        private CarRepository() {
        }

        public static com.carassurance.database.repository.CarRepository getInstance() {
            if (instance == null) {
                synchronized (com.carassurance.database.repository.CarRepository.class) {
                    if (instance == null) {
                        instance = new com.carassurance.database.repository.CarRepository();
                    }
                }
            }
            return instance;
        }

        public LiveData<CarEntity> getCar(final String plate, Application application) {
            return AppDatabase.getInstance(application).carDao().getByPlate(plate);
        }

        public LiveData<List<CarEntity>> getAllCar(Application application) {
            return AppDatabase.getInstance(application).carDao().getAllCar();
        }

        public LiveData<List<CarEntity>> getAllCarByOwner(final String owner, Application application) {
             return AppDatabase.getInstance(application).carDao().getAllCarByOwner(owner);
        }

        public void insert(final CarEntity car, OnAsyncEventListener callback, Application application) {
            new CreateCar(application, callback).execute(car);
        }

    }



