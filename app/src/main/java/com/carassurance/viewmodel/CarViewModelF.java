package com.carassurance.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.carassurance.BaseApp;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.CarRepositoryF;
import com.carassurance.util.OnAsyncEventListener;

public class CarViewModelF extends AndroidViewModel {
    private static final String TAG = "IncidentViewModel";
    private CarRepositoryF repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<CarEntityF> observableCar;

    public CarViewModelF(@NonNull Application application,
                         final String car_Id, CarRepositoryF carRepository) {
        super(application);

        repository = carRepository;

        this.application = application;

        observableCar = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCar.setValue(null);

        LiveData<CarEntityF> car = repository.getCar(car_Id);

        // observe the changes of the client entity from the database and forward them
        observableCar.addSource(car, observableCar::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;


        private final String idCar;

        private final CarRepositoryF repository;

        public Factory(@NonNull Application application, String idCar) {
            this.application = application;
            this.idCar = idCar;
            repository = ((BaseApp) application).getCarRepositoryF();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarViewModelF(application, idCar, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<CarEntityF> getCar() {
        return observableCar;
    }

    public void update(CarEntityF car, OnAsyncEventListener callback) {
        repository.update(car, callback);
    }

    public void insert(CarEntityF car, OnAsyncEventListener callback) {
        repository.insert(car, callback);
    }

    public void delete(CarEntityF car, OnAsyncEventListener callback) {
        repository.delete(car, callback);
    }

}
