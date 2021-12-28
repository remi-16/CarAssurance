package com.carassurance.database.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.carassurance.BaseApp;
import com.carassurance.database.async.user.CreateUser;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.entity.UserEntityF;
import com.carassurance.database.firebase.UserLiveData;
import com.carassurance.database.pojo.CarsWithUser;
import com.carassurance.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserRepositoryF {

    private static final String TAG = "UserRepositoryF";
    private static UserRepositoryF instance;

    private UserRepositoryF() {
    }

    public static UserRepositoryF getInstance() {
        if (instance == null) {
            synchronized (CarRepository.class) {
                if (instance == null) {
                    instance = new UserRepositoryF();
                }
            }
        }
        return instance;
    }

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public LiveData<UserEntityF> getUser(final String clientId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(clientId);
        return new UserLiveData(reference);
    }

    public void register(final UserEntityF user, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(user, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }



    private void insert(final UserEntityF user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                        Log.d(TAG, "Rollback successful: User account deleted");
                                    } else {
                                        callback.onFailure(task.getException());
                                        Log.d(TAG, "Rollback failed: signInWithEmail:failure",
                                                task.getException());
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<CarsWithUser>> getAllCarByOwner(final String id, Application application) {
        return ((BaseApp) application).getDatabase().userDao().getAllCarByOwner(id);
    }

    public LiveData<List<UserEntity>> getAllUsers(Application application) {
        return ((BaseApp) application).getDatabase().userDao().getAll();
    }

}

