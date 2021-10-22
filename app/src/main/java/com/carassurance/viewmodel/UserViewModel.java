package com.carassurance.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.util.OnAsyncEventListener;


public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<UserEntity> observableClient;

    public UserViewModel(@NonNull Application application,
                           final String email, UserRepository clientRepository) {
        super(application);

        repository = clientRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<UserEntity> client = repository.getClient(email, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(client, observableClient::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final UserRepository repository;

        public Factory(@NonNull Application application, String clientEmail) {
            this.application = application;
            this.email = clientEmail;
            repository = UserRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<UserEntity> getClient() {
        return observableClient;
    }

    public void createClient(UserEntity client, OnAsyncEventListener callback) {
        repository.insert(client, callback, applicationContext);
    }

}