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
import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.util.OnAsyncEventListener;


public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<UserEntity> observableUser;

    public UserViewModel(@NonNull Application application,
                           final String email, UserRepository userRepository) {
        super(application);

        this.application = application;

        repository = userRepository;

        observableUser = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableUser.setValue(null);

        LiveData<UserEntity> client = repository.getUser(email, application);

        // observe the changes of the client entity from the database and forward them
        observableUser.addSource(client, observableUser::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final UserRepository repository;

        public Factory(@NonNull Application application, String email) {
            this.application = application;
            this.email = email;
            repository = ((BaseApp) application).getUserRepository();
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
    public LiveData<UserEntity> getUser() {
        return observableUser;
    }

    public void createUser(UserEntity user, OnAsyncEventListener callback) {
        repository.insert(user, callback, application);
    }

}