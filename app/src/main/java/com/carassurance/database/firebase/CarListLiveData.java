package com.carassurance.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.CarEntityF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarListLiveData extends LiveData<List<CarEntityF>> {
    private static final String TAG = "CarListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final MyValueEventListener listener = new MyValueEventListener();

    public CarListLiveData(DatabaseReference ref, String owner) {
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
            setValue(toCars(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CarEntityF> toCars(DataSnapshot snapshot) {
        List<CarEntityF> cars = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CarEntityF entity = childSnapshot.getValue(CarEntityF.class);
            entity.setId(childSnapshot.getKey());
            entity.setOwner(owner);
            cars.add(entity);
        }
        return cars;
    }
}
