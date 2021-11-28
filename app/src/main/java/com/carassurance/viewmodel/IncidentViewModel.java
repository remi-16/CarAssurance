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
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.util.OnAsyncEventListener;

public class IncidentViewModel extends AndroidViewModel {

    private IncidentRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<IncidentEntity> observableIncident;

    public IncidentViewModel(@NonNull Application application,
                         final Long id, IncidentRepository incidentRepository) {
        super(application);

        repository = incidentRepository;

        this.application = application;

        observableIncident = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableIncident.setValue(null);

        LiveData<IncidentEntity> incident = repository.getIncidentById(id, application);

        // observe the changes of the client entity from the database and forward them
        observableIncident.addSource(incident, observableIncident::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final Long id;

        private final IncidentRepository repository;

        public Factory(@NonNull Application application, Long id) {
            this.application = application;
            this.id = id;
            repository = ((BaseApp) application).getIncidentRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new IncidentViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<IncidentEntity> getIncident() {
        return observableIncident;
    }

    public void create(IncidentEntity incident, OnAsyncEventListener callback) {
        repository.insert(incident, callback, application);
    }

    public void update(IncidentEntity incident, OnAsyncEventListener callback) {
        repository.update(incident, callback, application);
    }


    public void delete(IncidentEntity incident, OnAsyncEventListener callback) {
        repository.delete(incident, callback, application);
    }

}
