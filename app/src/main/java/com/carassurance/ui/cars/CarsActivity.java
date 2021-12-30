package com.carassurance.ui.cars;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.database.repository.UserRepositoryF;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.fragments.CarFragment;
import com.carassurance.viewmodel.UserViewModel;
import com.carassurance.viewmodel.UserViewModelF;
import com.google.firebase.auth.FirebaseAuth;

/**
 * cette class affiche l'interafce de toutes les voitures d'un utilisateur
 */
public class CarsActivity extends BaseActivity {

    private TextView textCar;
    private UserRepositoryF repository  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);
        textCar = (TextView) findViewById(R.id.MyCars);
        repository= ((BaseApp) getApplication()).getUserRepositoryF();
        initvar();


    }


    /**
     * cette methode récupère la liste des voitures de l'utilisateurs et l'envoie au fragment
     */
    private void initvar(){
       SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String userID;

        UserViewModelF.Factory factory = new UserViewModelF.Factory(
                getApplication(),
                userID = FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

        repository.getAllCarByOwner(userID).observe(CarsActivity.this, carEntityFList -> {
            if (carEntityFList != null) {
                mCars = carEntityFList;

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.carfragmentContainerView, CarFragment.class, null)
                        .commit();

                getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);
            }

        });
    }
}