package com.carassurance.database;

import android.os.AsyncTask;
import android.util.Log;

import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.encryption.HashPassword;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addUser(final AppDatabase db,  final String email, final String firstName,
                                  final String lastName, final String password) {
        UserEntity client = new UserEntity(email, firstName, lastName, password);
        db.userDao().insert(client);
    }
    private static void addCar(final AppDatabase db,  final String plate, final String brand,
                                  final String model, final String Color, final String owner) {
        CarEntity car = new CarEntity(plate, brand, model, Color, owner);
        db.carDao().insert(car);
    }

    private static void populateWithTestData(AppDatabase db) {
        HashPassword hashPassword = new HashPassword();
        db.userDao().deleteAll();
        db.carDao().deleteAll();

        addUser(db, "remi.cohu@gmail.com","Cohu","Rémi", hashPassword.hash("Soleil123"));
        addCar(db,"FR339484", "Mazda", "dynamyx","Blanc", "remi.cohu@gmail.com" );

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
