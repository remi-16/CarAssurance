package com.carassurance.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.carassurance.database.entity.IncidentEntityF;
import com.carassurance.database.entity.UserEntityF;
import com.carassurance.database.pojo.IncidentsWithUserF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserIncidentsListLiveData extends LiveData<List<IncidentsWithUserF>> {
    private static final String TAG = "UserIncidentsListLiveData";

    private final DatabaseReference reference;
    private final String owner;
    private final UserIncidentsListLiveData.MyValueEventListener listener =
            new UserIncidentsListLiveData.MyValueEventListener();

    public UserIncidentsListLiveData(DatabaseReference ref, String owner) {
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
            setValue(toIncidentsWithUserFList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<IncidentsWithUserF> toIncidentsWithUserFList(DataSnapshot snapshot) {
        List<IncidentsWithUserF> incidentsWithUserFList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().equals(owner)) {
                IncidentsWithUserF incidentsWithUserF = new IncidentsWithUserF();
                incidentsWithUserF.user = childSnapshot.getValue(UserEntityF.class);
                incidentsWithUserF.user.setId(childSnapshot.getKey());
                incidentsWithUserF.incidents = toIncidents(childSnapshot.child("incidents"),
                        childSnapshot.getKey());
                incidentsWithUserFList.add(incidentsWithUserF);
            }
        }
        return incidentsWithUserFList;
    }

    private List<IncidentEntityF> toIncidents(DataSnapshot snapshot, String ownerId) {
        List<IncidentEntityF> incidents = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            IncidentEntityF entity = childSnapshot.getValue(IncidentEntityF.class);
            entity.setId(childSnapshot.getKey());
            entity.setClient(ownerId);
            incidents.add(entity);
        }
        return incidents;
    }
}
