package com.carassurance.ui.cars;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.fragments.CarFragment;
import com.carassurance.viewmodel.UserViewModel;

/**
 * cette class affiche l'interafce de toutes les voitures d'un utilisateur
 */
public class CarsActivity extends BaseActivity {

    private TextView textCar;
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


    /**
     * cette methode récupère la liste des voitures de l'utilisateurs et l'envoie au fragment
     */
    private void initvar(){
       SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String useremail = settings.getString(PREFS_USER, null);
        repository.getAllCarByOwner(useremail, getApplication()).observe(CarsActivity.this, userEntity -> {
            if (userEntity != null) {
                mCars = userEntity.get(0).cars;

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.carfragmentContainerView, CarFragment.class, null)
                        .commit();

                getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);
            }

        });
    }
}