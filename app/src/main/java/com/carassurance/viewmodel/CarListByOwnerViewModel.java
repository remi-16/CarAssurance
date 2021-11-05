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
import com.carassurance.database.repository.CarRepository;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;

public class CarListByOwnerViewModel extends AndroidViewModel {
    private Application application;

    private CarRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.

    private final MediatorLiveData<List<CarEntity>> observableCarList;

    public CarListByOwnerViewModel(@NonNull Application application,
                                final String ownerId,
                                CarRepository carRepository) {
        super(application);

        this.application = application;

        repository = carRepository;


        observableCarList = new MediatorLiveData<>();
        // set by default null, until we get data from the database.

        observableCarList.setValue(null);


        LiveData<List<CarEntity>> listcar = repository.getAllCarByOwner(ownerId, application);


        observableCarList.addSource(listcar, observableCarList::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String ownerId;


        private final CarRepository carRepository;

        public Factory(@NonNull Application application, String ownerId) {
            this.application = application;
            this.ownerId = ownerId;
            carRepository = ((BaseApp) application).getCarRepository();

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListByOwnerViewModel(application, ownerId, carRepository);
        }
    }




    public LiveData<List<CarEntity>> getListCarByOwner() {
        return observableCarList;
    }




}
