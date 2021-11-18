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
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.pojo.CarsWithUser;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;

public class CarListByOwnerViewModel extends AndroidViewModel {
    private Application application;


    private CarRepository cRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.

    private final MediatorLiveData<List<CarsWithUser>> observableCarListWithUser;

    private final MediatorLiveData<List<CarEntity>> observableCars;

    public CarListByOwnerViewModel(@NonNull Application application,
                                        final String ownerId,
                                   UserRepository userRepository, CarRepository carRepository) {
        super(application);

        this.application = application;


        cRepository= carRepository;



        observableCarListWithUser = new MediatorLiveData<>();
        observableCars = new MediatorLiveData<>();
        // set by default null, until we get data from the database.

        observableCarListWithUser.setValue(null);
        observableCars.setValue(null);


        LiveData<List<CarsWithUser>> user = userRepository.getAllCarByOwner(ownerId, application);
        LiveData<List<CarEntity>> carlist = cRepository.getAllCar(application);

        observableCarListWithUser.addSource(user, observableCarListWithUser::setValue);
        observableCars.addSource(carlist, observableCars::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String ownerId;


        private final UserRepository userRepository;
        private final CarRepository carRepository;

        public Factory(@NonNull Application application, String ownerId) {
            this.application = application;
            this.ownerId = ownerId;
            userRepository = ((BaseApp) application).getUserRepository();
            carRepository = ((BaseApp) application).getCarRepository();

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CarListByOwnerViewModel(application, ownerId, userRepository,carRepository);
        }
    }



    public LiveData<List<CarEntity>> getOwnCar() {
        return observableCars;
    }
    public LiveData<List<CarsWithUser>> getListByOwner() {
        return observableCarListWithUser;
    }



}
