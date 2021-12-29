package com.carassurance.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.UserEntityF;
import com.carassurance.database.pojo.CarsWithUserF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserCarsListLiveData extends LiveData<List<CarsWithUserF>> {
    private static final String TAG = "UserCarsListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final UserCarsListLiveData.MyValueEventListener listener =
            new UserCarsListLiveData.MyValueEventListener();

    public UserCarsListLiveData(DatabaseReference ref, String owner) {
        reference = ref;
        this.owner = owner;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toCarsWithUserFList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CarsWithUserF> toCarsWithUserFList(DataSnapshot snapshot) {
        List<CarsWithUserF> carsWithUserFListList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().equals(owner)) {
                CarsWithUserF carsWithUserF = new CarsWithUserF();
                carsWithUserF.user = childSnapshot.getValue(UserEntityF.class);
                carsWithUserF.user.setId(childSnapshot.getKey());
                carsWithUserF.cars = toCars(childSnapshot.child("accounts"),
                        childSnapshot.getKey());
                carsWithUserFListList.add(carsWithUserF);
            }
        }
        return carsWithUserFListList;
    }

    private List<CarEntityF> toCars(DataSnapshot snapshot, String ownerId) {
        List<CarEntityF> cars = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CarEntityF entity = childSnapshot.getValue(CarEntityF.class);
            entity.setId(childSnapshot.getKey());
            entity.setOwner(ownerId);
            cars.add(entity);
        }
        return cars;
    }
}
