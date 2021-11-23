package com.carassurance.ui.incidents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.carassurance.R;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.util.OnAsyncEventListener;
import com.carassurance.viewmodel.IncidentViewModel;

public class EditIncidentActivity extends BaseActivity {
    private TextView email;
    private TextView plate;
    private TextView status;
    private TextView date;
    private TextView place;
    private TextView type;
    private TextView description;
    private TextView number;
    private CheckBox injured;
    private Button edit;
    private Button del;
    private Button save;


    private IncidentEntity mIncident;
    private IncidentViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_editincidents, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);
        Long incidentId = getIntent().getLongExtra("incidentId", 0);
        String plateCar = getIntent().getExtras().getString("plate");
        email = findViewById(R.id.emailclient);
        plate= findViewById(R.id.incplate);
        status= findViewById(R.id.stat);
        date= findViewById(R.id.date);
        place= findViewById(R.id.place);
        type = findViewById(R.id.type);
        description= findViewById(R.id.description);
        number= findViewById(R.id.inc_numb);
        injured = findViewById(R.id.checkBoxInjured);
        edit = findViewById(R.id.btn_incedit);
        del = findViewById(R.id.btn_incdel);
        save =findViewById(R.id.btn_incsave);
        save.setVisibility(View.INVISIBLE);
        description.setFocusable(false);
        injured.setFocusable(false);


        IncidentViewModel.Factory factory = new IncidentViewModel.Factory(getApplication(), incidentId);
        viewModel = ViewModelProviders.of(this, factory).get(IncidentViewModel.class);
        viewModel.getIncident().observe(this, incidentEntity -> {
            if (incidentEntity != null) {
                mIncident=incidentEntity;
                email.setText(incidentEntity.getClient());
                status.setText(incidentEntity.getStatus());
                date.setText(incidentEntity.getDate());
                place.setText(incidentEntity.getLocation());
                type.setText(incidentEntity.getType());
                description.setText(incidentEntity.getDescription());
                number.setText(incidentId +"");
                injured.setChecked(incidentEntity.getInjured());
                plate.setText(plateCar);
                if (!incidentEntity.getStatus().equalsIgnoreCase("Ouvert")){
                    edit.setVisibility(View.INVISIBLE);
                    del.setVisibility(View.INVISIBLE);
                }else{
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            description.setFocusableInTouchMode(true);
                            injured.setFocusableInTouchMode(true);
                            save.setVisibility(View.VISIBLE);
                            edit.setVisibility(View.INVISIBLE);
                            del.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.delete(mIncident, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        goToincident();
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIncident.setDescription(description.getText().toString());
                if (injured.isActivated()){
                    mIncident.setInjured(true);
                }else{
                    mIncident.setInjured(false);
                }


                viewModel.update(mIncident, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {

                        goToincident();
                        save.setVisibility(View.INVISIBLE);
                        del.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.VISIBLE);
                        description.setFocusable(false);
                        injured.setChecked(false);

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

        });

    }
    public void goToincident(){
        Intent i = new Intent( this , IncidentsActivity. class );
        startActivity(i);
    }
}
