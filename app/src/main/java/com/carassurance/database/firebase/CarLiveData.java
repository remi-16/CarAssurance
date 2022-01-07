package com.carassurance.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.CarEntityF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class CarLiveData extends LiveData<CarEntityF> {
    private static final String TAG = "CarLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final CarLiveData.MyValueEventListener listener = new CarLiveData.MyValueEventListener();

    public CarLiveData(DatabaseReference ref) {
        reference = ref;
        owner = ref.getParent().getParent().getKey();
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
            try{
                CarEntityF entity = dataSnapshot.getValue(CarEntityF.class);
                entity.setId(dataSnapshot.getKey());
                entity.setOwner(owner);
                setValue(entity);
            }catch(Exception e){
                //pour suppression
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
