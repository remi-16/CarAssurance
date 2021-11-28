package com.carassurance.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.carassurance.R;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.ui.incidents.IncidentsActivity;
import com.carassurance.ui.report.ReportActivity;
import com.carassurance.viewmodel.UserViewModel;

/**
 * Class AppActivity est l'activité principale de notre application
 */
public class AppActivity extends BaseActivity{
    private Button report;
    private Button incidents;
    private Button cars;
    private TextView lastname;
    private TextView firstname;
    private TextView email;
    private UserViewModel viewModel;

    private UserEntity user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_app, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.VISIBLE);

        report = findViewById(R.id.buttonReport);
        incidents = findViewById(R.id.buttonIncidents);
        cars = findViewById(R.id.buttonCars);
        lastname = findViewById(R.id.inc_numb);
        firstname = findViewById(R.id.stat);
        email = findViewById(R.id.emailclient);

        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String useremail = settings.getString(PREFS_USER, null);

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), useremail);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;
                lastname.setText(user.getLastname());
                firstname.setText(user.getFirstname());
                email.setText(user.getEmail());
            }
        });

        report .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToReport();
            }
        });
        incidents .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToIncidents();
            }
        });
        cars.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCars();
            }
        });

    }

    /**
     * goToReport ouvre l'activity qui permet de déclarer un sinistre
     */
    public void goToReport(){
        Intent i = new Intent( this , ReportActivity. class );
        startActivity(i);
    }

    /**
     * goToCars ouvre l'activity qui permet de voir ses voitures
     */
    public void goToCars(){
        Intent i = new Intent( this , CarsActivity. class );
        startActivity(i);
    }

    /**
     * goToIncidents ouvre l'activity qui permet de voir ses incidents
     */
    public void goToIncidents(){
        Intent i = new Intent( this , IncidentsActivity. class );
        startActivity(i);
    }

    @Override
    public void onBackPressed() {

    }
}
