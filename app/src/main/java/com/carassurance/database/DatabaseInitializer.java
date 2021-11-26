package com.carassurance.database;

import android.os.AsyncTask;
import android.util.Log;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.encryption.HashPassword;

import java.util.Date;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addUser(final AppDatabase db,  final String email, final String firstName,
                                  final String lastName, final String password) {
        UserEntity user = new UserEntity(email, firstName, lastName, password);
        db.userDao().insert(user);
    }
    private static void addCar(final AppDatabase db,  final String plate, final String brand,
                                  final String model, final String Color, final String owner) {
        CarEntity car = new CarEntity(plate, brand, model, Color, owner);
        db.carDao().insert(car);
    }

    private static void addIncident(final AppDatabase db,   final String client,
                               final long car_id, final String location, final String date, final String type, final Boolean injured, final String urlMoreInfo, final String desciption) {
        IncidentEntity incident = new IncidentEntity(client, car_id, location, date, type, injured,urlMoreInfo,desciption);
        db.incidentDao().insert(incident);
    }
    private static void addIncident2(final AppDatabase db,   IncidentEntity ie){
        db.incidentDao().insert(ie);
    }

    private static void populateWithTestData(AppDatabase db) {
        HashPassword hashPassword = new HashPassword();
        db.incidentDao().deleteAll();
        db.carDao().deleteAll();
        db.userDao().deleteAll();



        addUser(db, "remi.cohu@gmail.com","Cohu","Rémi", hashPassword.hash("Soleil123"));
        try {
            // Let's ensure that the user are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addCar(db,"FR339484", "Mazda", "dynamyx","Blanc", "remi.cohu@gmail.com" );
        try {
            // Let's ensure that the car are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addIncident(db,"remi.cohu@gmail.com", 1,"Rte du centre 102, 1723 Marly","22.10.2021", "collision",false,null,"collision avec un mur");
        addIncident(db,"remi.cohu@gmail.com", 1,"Rte du centre 102, 1723 Marly","22.10.2021","vol", false,null,null);
        IncidentEntity ie = new IncidentEntity("remi.cohu@gmail.com", 1L,"Rte du centre 102, 1723 Marly","01.10.2021","vol", false,null,null);
        ie.setStatus("Fermer");
        addIncident2(db,ie);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
