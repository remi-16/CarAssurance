package com.carassurance.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.IncidentEntityF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class IncidentLiveData extends LiveData<IncidentEntityF> {
    private static final String TAG = "IncidentLiveData";

    private final DatabaseReference reference;
    private final String client;
    private final String car_id;
    private final IncidentLiveData.MyValueEventListener listener = new IncidentLiveData.MyValueEventListener();

    public IncidentLiveData(DatabaseReference ref) {
        reference = ref;
        client = ref.getParent().getParent().getParent().getKey();
        car_id = ref.getParent().getParent().getKey();
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
            IncidentEntityF entity = dataSnapshot.getValue(IncidentEntityF.class);
            try{
                entity.setId(dataSnapshot.getKey());
                entity.setClient(client);
                entity.setCar_id(car_id);
                setValue(entity);
            }catch(Exception e){
                //pour la suppréssion
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
