package com.carassurance;

import android.app.Application;

import com.carassurance.database.AppDatabase;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.CarRepositoryF;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.database.repository.IncidentRepositoryF;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.database.repository.UserRepositoryF;

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

   public CarRepositoryF getCarRepositoryF() {
        return CarRepositoryF.getInstance();
    }

    public UserRepositoryF getUserRepositoryF() {
        return UserRepositoryF.getInstance();
    }

    public IncidentRepositoryF getIncidentRepositoryF() {
        return IncidentRepositoryF.getInstance();
    }
}
