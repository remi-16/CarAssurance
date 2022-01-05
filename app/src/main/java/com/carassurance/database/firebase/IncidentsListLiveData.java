package com.carassurance.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.IncidentEntityF;
import com.carassurance.database.pojo.IncidentWithCarF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IncidentsListLiveData extends LiveData<List<IncidentWithCarF>> {
    private static final String TAG = "IncidentLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final IncidentsListLiveData.MyValueEventListener listener = new IncidentsListLiveData.MyValueEventListener();

    public IncidentsListLiveData(DatabaseReference ref, String owner) {
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
            setValue(toIncidentWithCar(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<IncidentWithCarF> toIncidentWithCar(DataSnapshot snapshot) {
            List<IncidentWithCarF> incidentEntityF = new ArrayList<>();
            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                CarEntityF entity = childSnapshot.getValue(CarEntityF.class);
                entity.setId(childSnapshot.getKey());
                entity.setOwner(owner);
                for (DataSnapshot childIncidentSnapshot : childSnapshot.child("incidents").getChildren()){
                    if (childIncidentSnapshot!=null){
                        IncidentEntityF incident = childIncidentSnapshot.getValue(IncidentEntityF.class);
                        incident.setId(childIncidentSnapshot.getKey());
                        incident.setClient(owner);
                        incident.setCar_id(childSnapshot.getKey());
                        IncidentWithCarF incidentWithCarF= new IncidentWithCarF(incident,entity);
                        incidentEntityF.add(incidentWithCarF);
                    }
                }
            }

        return incidentEntityF;
    }
}
