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
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.IncidentRepository;

import java.util.List;

public class IncidentListByOwnerViewModel extends AndroidViewModel {
    private Application application;

    private IncidentRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.

    private final MediatorLiveData<List<IncidentEntity>> observableIncidentList;

    public IncidentListByOwnerViewModel(@NonNull Application application,
                                        final String ownerId,
                                        IncidentRepository incidentRepository) {
        super(application);

        this.application = application;

        repository = incidentRepository;


        observableIncidentList = new MediatorLiveData<>();
        // set by default null, until we get data from the database.

        observableIncidentList.setValue(null);


        LiveData<List<IncidentEntity>> listincident = repository.getAllIncidentByUser(ownerId, application);


        observableIncidentList.addSource(listincident, observableIncidentList::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String ownerId;


        private final IncidentRepository incidentRepository;

        public Factory(@NonNull Application application, String ownerId) {
            this.application = application;
            this.ownerId = ownerId;
            incidentRepository = ((BaseApp) application).getIncidentRepository();

        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new IncidentListByOwnerViewModel(application, ownerId, incidentRepository);
        }
    }




    public LiveData<List<IncidentEntity>> getListByOwner() {
        return observableIncidentList;
    }




}
