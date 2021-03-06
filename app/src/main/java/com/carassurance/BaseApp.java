package com.carassurance;

import android.app.Application;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.database.repository.UserRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public CarRepository getCarRepository() {
        return CarRepository.getInstance();
    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

    public IncidentRepository getIncidentRepository() {
        return IncidentRepository.getInstance();
    }
}
