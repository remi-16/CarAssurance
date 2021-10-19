package com.carassurance.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.carassurance.R;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.ui.incidents.IncidentsActivity;
import com.carassurance.ui.report.ReportActivity;

public class AppActivity extends BaseActivity{
    private Button report;
    private Button incidents;
    private Button cars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_app, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.VISIBLE);

        report = findViewById(R.id.buttonReport);
        incidents = findViewById(R.id.buttonIncidents);
        cars = findViewById(R.id.buttonCars);

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

    public void goToReport(){
        Intent i = new Intent( this , ReportActivity. class );
        startActivity(i);
    }
    public void goToCars(){
        Intent i = new Intent( this , CarsActivity. class );
        startActivity(i);
    }
    public void goToIncidents(){
        Intent i = new Intent( this , IncidentsActivity. class );
        startActivity(i);
    }
}
