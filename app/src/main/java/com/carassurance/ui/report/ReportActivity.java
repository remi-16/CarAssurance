package com.carassurance.ui.report;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.AppActivity;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.ui.cars.fragments.CarFragment;
import com.carassurance.ui.incidents.IncidentsActivity;
import com.carassurance.ui.report.fragments.ReportIncidentTypeFragment;
import com.carassurance.ui.report.fragments.ReportDescriptionFragment;
import com.carassurance.util.OnAsyncEventListener;
import com.carassurance.viewmodel.CarViewModel;
import com.carassurance.viewmodel.IncidentViewModel;

import java.time.LocalDateTime;
import java.util.Date;


public class ReportActivity extends BaseActivity implements View.OnClickListener {

    private ReportVM viewModel;

    private SharedPreferences settings;

    private IncidentRepository mIncidentRepository;
    private UserRepository mUserRepository;
    private IncidentViewModel mIncidentViewModel;

    private Button mCancelButton;
    private Button mNextButton;
    private Button mBackButton;
    public String[] mIncidentTypes = {"Accident", "Brise de glace", "Dégat fouine", "Vol"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ReportVM.class);
        viewModel.getSelectedItem().observe(this, item -> {
            // Perform an action with the latest item data

        });

        mIncidentRepository= ((BaseApp) getApplication()).getIncidentRepository();
        mUserRepository = ((BaseApp) getApplication()).getUserRepository();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, ReportIncidentTypeFragment.class, null)
                    .commit();
        }


        getLayoutInflater().inflate(R.layout.activity_report,frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);

        mCancelButton = findViewById(R.id.buttonCancel);
        mNextButton = findViewById(R.id.buttonNext);
        mBackButton = findViewById(R.id.buttonBack);

        mCancelButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        IncidentViewModel.Factory factory = new IncidentViewModel.Factory(getApplication(), null);
        mIncidentViewModel = ViewModelProviders.of(this, factory).get(IncidentViewModel.class);

        settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);

        String useremail = settings.getString(PREFS_USER, null);

        mUserRepository.getAllCarByOwner(useremail, getApplication()).observe(this, userEntity -> {
            if (userEntity != null) {
                mCars = userEntity.get(0).cars;
            }
        });


    }

    private void insertIncident(){
        String useremail = settings.getString(PREFS_USER, null);
        IncidentEntity incidentEntity = new IncidentEntity(useremail,(long)1,"test", "24.11.2021",viewModel.getType(),false,"",viewModel.getDescription());
        mIncidentViewModel.create(incidentEntity, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                mCancelButton.setText("Réussi!");
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view == mNextButton) {

            if(viewModel.getCheckNext() == true){
                    /*getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("Test")
                            .replace(R.id.fragment_container_view, ReportDescriptionFragment.class, null)
                            .commit();*/

                switch (viewModel.getActualFragment()){
                    case 1:
                        viewModel.setActualFragment(2);
                        replaceFragment(CarFragment.class);
                        break;
                    case 2:
                        viewModel.setActualFragment(3);
                        mNextButton.setText("Enregistrer");
                        replaceFragment(ReportDescriptionFragment.class);
                        break;
                    case 3:
                        insertIncident();
                }

            }
        } else if (view == mBackButton) {
            mNextButton.setText("Suivant");
            viewModel.setActualFragment(viewModel.getActualFragment() -1);
            getSupportFragmentManager().popBackStack();

        } else if (view == mCancelButton) {
            Intent i = new Intent( this , AppActivity. class );
            startActivity(i);
        }
    }

    private void replaceFragment(Class fragment){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack(fragment.toString())
                .replace(R.id.fragment_container_view, fragment, null)
                .commit();
    }
}
