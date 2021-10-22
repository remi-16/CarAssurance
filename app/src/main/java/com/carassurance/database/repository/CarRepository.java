package com.carassurance.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.async.CreateCar;
import com.carassurance.database.entity.CarEntity;
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

        public LiveData<CarEntity> getCar(final String plate, Context context) {
            return AppDatabase.getInstance(context).carDao().getByPlate(plate);
        }

        public LiveData<List<CarEntity>> getAllCar(Context context) {
            return AppDatabase.getInstance(context).carDao().getAll();
        }

        public void insert(final CarEntity car, OnAsyncEventListener callback, Context context) {
            new CreateCar(context, callback).execute(car);
        }

    }



