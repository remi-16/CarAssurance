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
import com.carassurance.database.pojo.CarsWithUser;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.util.OnAsyncEventListener;

import java.util.List;


public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<UserEntity> observableUser;
    private final MediatorLiveData<List<CarsWithUser>> observableUserWithCar;

    public UserViewModel(@NonNull Application application,
                           final String userId, UserRepository userRepository) {
        super(application);

        this.application = application;

        repository = userRepository;

        observableUser = new MediatorLiveData<>();
        observableUserWithCar = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableUser.setValue(null);
        observableUserWithCar.setValue(null);

        LiveData<UserEntity> user = repository.getUser(userId, application);
        LiveData<List<CarsWithUser>> usercars = repository.getAllCarByOwner(userId, application);

        // observe the changes of the client entity from the database and forward them
        observableUser.addSource(user, observableUser::setValue);
        observableUser.addSource(usercars, observableUserWithCar::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String userId;

        private final UserRepository repository;

        public Factory(@NonNull Application application, String userId) {
            this.application = application;
            this.userId = userId;
            repository = ((BaseApp) application).getUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, userId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<UserEntity> getUser() {
        return observableUser;
    }

    public LiveData<List<CarsWithUser>> getUserWithCar() {
        return observableUserWithCar;
    }

    public void createUser(UserEntity user, OnAsyncEventListener callback) {
        repository.insert(user, callback, application);
    }

}