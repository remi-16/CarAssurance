package com.carassurance.database;

import android.os.AsyncTask;
import android.util.Log;

import com.carassurance.database.entity.UserEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addClient(final AppDatabase db, final String email, final String firstName,
                                  final String lastName) {
        UserEntity client = new UserEntity(email, firstName, lastName, "");
        db.userDao().insert(client);
    }

    private static void populateWithTestData(AppDatabase db) {
      //  db.userDao().deleteAll();

        addClient(db, "michel.platini@fifa.com", "Michel", "Platini");
        addClient(db, "sepp.blatter@fifa.com", "Sepp", "Blatter");
        addClient(db, "ebbe.schwartz@fifa.com", "Ebbe", "Schwartz");
        addClient(db, "aleksander.ceferin@fifa.com", "Aleksander", "Ceferin");
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
