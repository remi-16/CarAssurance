package com.carassurance.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.carassurance.BaseApp;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;

public class CarViewModel extends AndroidViewModel {

    private CarRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<CarEntity> observableCar;

    public CarViewModel(@NonNull Application application,
                         final String plate, CarRepository carRepository) {
        super(application);

        repository = carRepository;

        this.application = application;

        observableCar = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCar.setValue(null);

        LiveData<CarEntity> car = repository.getCar(plate, application);

        // observe the changes of the client entity from the database and forward them
        observableCar.addSource(car, observableCar::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;


        private final String plate;

        private final CarRepository repository;

        public Factory(@NonNull Application application, String plate) {
            this.application = application;
            this.plate = plate;
            repository = ((BaseApp) application).getCarRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarViewModel(application, plate, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<CarEntity> getCar() {
        return observableCar;
    }

    public void updateCar(CarEntity car, OnAsyncEventListener callback) {
        repository.update(car, callback, application);
    }

    public void insertCar(CarEntity car, OnAsyncEventListener callback) {
        repository.insert(car, callback, application);
    }

    public void deletCar(CarEntity car, OnAsyncEventListener callback) {
        repository.delete(car, callback, application);
    }

}
