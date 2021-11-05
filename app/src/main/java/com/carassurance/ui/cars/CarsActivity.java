package com.carassurance.ui.cars;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.repository.CarRepository;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.LoginActivity;
import com.carassurance.ui.cars.fragments.CarFragment;
import com.carassurance.viewmodel.CarListByOwnerViewModel;

import java.util.ArrayList;
import java.util.List;

public class CarsActivity extends BaseActivity {

    private TextView textCar;
    private CarListByOwnerViewModel carListByOwnerViewModel;
    private CarRepository repository;
    private CarListByOwnerViewModel viewModel;
    private List<CarEntity> mCars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);
        textCar = (TextView) findViewById(R.id.MyCars);
       /* viewModel = new ViewModelProvider(this).get(CarListByOwnerViewModel.class);
        viewModel.getListCarByOwner().observe(this, carList -> {
            if (carList != null) {
                mCars = carList;
            }
        });*/

        repository = ((BaseApp) getApplication()).getCarRepository();
        initvar();

        Fragment f = new CarFragment();
        loadFragment(f);

    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        /*  Bundle bundle = new Bundle();
        ArrayList<CarEntity> cars = new ArrayList<>(mCars.size());
        cars.addAll(mCars);
        bundle.putSerializable("cars",  cars);
        fragment.setArguments(bundle);*/
        fragmentTransaction.replace(R.id.carfragmentContainerView, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private void initvar(){
        SharedPreferences settings = this.getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String useremail = settings.getString(BaseActivity.PREFS_USER, null);

        repository.getAllCarByOwner(useremail, getApplication()).observe(CarsActivity.this, carList -> {
            if (carList != null) {
                mCars = carList;

            }else{
                mCars.add(new CarEntity("Erreur",null, null, null,null));
            }
        });
    }
}
