package com.carassurance.ui.cars;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



import androidx.lifecycle.ViewModelProviders;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.pojo.CarsWithUser;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.LoginActivity;
import com.carassurance.ui.cars.fragments.CarFragment;
import com.carassurance.viewmodel.CarListByOwnerViewModel;
import com.carassurance.viewmodel.IncidentListByOwnerViewModel;
import com.carassurance.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class CarsActivity extends BaseActivity {

    private TextView textCar;
    private UserViewModel viewModel;
    public List<CarEntity> mCars;
    private UserRepository repository  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);
        textCar = (TextView) findViewById(R.id.MyCars);
        repository= ((BaseApp) getApplication()).getUserRepository();
        initvar();


    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.carfragmentContainerView, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private void initvar(){
       SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String useremail = settings.getString(PREFS_USER, null);
       /*  UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), useremail);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.getUserWithCar().observe(this, carsWithUser -> {*/
        repository.getAllCarByOwner(useremail, getApplication()).observe(CarsActivity.this, userEntity -> {
            if (userEntity != null) {
                mCars = userEntity.get(0).cars;
                Fragment f = new CarFragment();
                loadFragment(f);
                getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);
            }

        });
    }
}