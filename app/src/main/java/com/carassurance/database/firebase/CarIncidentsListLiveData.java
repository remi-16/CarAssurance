package com.carassurance.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.entity.IncidentEntityF;
import com.carassurance.database.pojo.IncidentsWithCarsF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CarIncidentsListLiveData extends LiveData<List<IncidentsWithCarsF>> {

    private static final String TAG = "CarIncidentsListLiveData";

    private final DatabaseReference reference;
    private final String car_id;
    private final CarIncidentsListLiveData.MyValueEventListener listener =
            new CarIncidentsListLiveData.MyValueEventListener();

    public CarIncidentsListLiveData(DatabaseReference ref, String car_id) {
        reference = ref;
        this.car_id = car_id;
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
            setValue(toIncidentsWithCarsF(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<IncidentsWithCarsF> toIncidentsWithCarsF(DataSnapshot snapshot) {
        List<IncidentsWithCarsF> incidentsWithCarsFList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().equals(car_id)) {
                IncidentsWithCarsF incidentsWithCarsF = new IncidentsWithCarsF();
                incidentsWithCarsF.car = childSnapshot.getValue(CarEntityF.class);
                incidentsWithCarsF.car.setId(childSnapshot.getKey());
                incidentsWithCarsF.incidents = toIncidents(childSnapshot.child("incidents"),
                        childSnapshot.getKey());
                incidentsWithCarsFList.add(incidentsWithCarsF);
            }
        }
        return incidentsWithCarsFList;
    }

    private List<IncidentEntityF> toIncidents(DataSnapshot snapshot, String car_id) {
        List<IncidentEntityF> incidents = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            IncidentEntityF entity = childSnapshot.getValue(IncidentEntityF.class);
            entity.setId(childSnapshot.getKey());
            entity.setCar_id(car_id);
            incidents.add(entity);
        }
        return incidents;
    }
}
