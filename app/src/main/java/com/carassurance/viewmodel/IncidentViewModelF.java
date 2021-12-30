package com.carassurance.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.carassurance.BaseApp;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.IncidentEntityF;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.database.repository.IncidentRepositoryF;
import com.carassurance.util.OnAsyncEventListener;

public class IncidentViewModelF extends AndroidViewModel {
    private static final String TAG = "IncidentViewModel";
    private IncidentRepositoryF repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<IncidentEntityF> observableIncident;

    public IncidentViewModelF(@NonNull Application application,
                              final String idCar, final String idIncident, IncidentRepositoryF incidentRepository) {
        super(application);

        repository = incidentRepository;

        this.application = application;

        observableIncident = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableIncident.setValue(null);
        if (idIncident !=null || idCar!=null){
            LiveData<IncidentEntityF> incident = repository.getIncidentById(idIncident,  idCar);

            // observe the changes of the client entity from the database and forward them
            observableIncident.addSource(incident, observableIncident::setValue);
        }

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String idIncident;

        private final String idCar;

        private final IncidentRepositoryF repository;

        public Factory(@NonNull Application application, String idIncident, String idCar) {
            this.application = application;
            this.idIncident = idIncident;
            this.idCar =  idCar;
            repository = ((BaseApp) application).getIncidentRepositoryF();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new IncidentViewModelF(application, idCar, idIncident,  repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<IncidentEntityF> getIncident() {
        return observableIncident;
    }

    public void create(IncidentEntityF incident, OnAsyncEventListener callback) {
        repository.insert(incident, callback);
    }

    public void update(IncidentEntityF incident, OnAsyncEventListener callback) {
        repository.update(incident, callback);
    }


    public void delete(IncidentEntityF incident, OnAsyncEventListener callback) {
        repository.delete(incident, callback);
    }

}
