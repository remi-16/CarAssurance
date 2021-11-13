package com.carassurance.ui.cars.fragments;

import android.annotation.SuppressLint;
//import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.fragment.app.Fragment;
import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.viewmodel.CarListByOwnerViewModel;
import com.carassurance.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;



public class CarFragment extends Fragment {

    private View view;
    private List<CarEntity> mCars;
    private ArrayList<CarEntity> cars;
    private Button mButtou;
    private ListView mListView;
    private CarListByOwnerViewModel viewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//      viewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(CarListByOwnerViewModel.class);
        view= inflater.inflate(R.layout.fragment_car, container, false);
        mButtou= (Button) view.findViewById(R.id.buttonAddCar);
        // Bundle bundle = this.getArguments();
        // cars = (ArrayList<CarEntity>) bundle.getSerializable("cars");
        mListView = (ListView) view.findViewById(R.id.carsListView);
        initVar();
        //  Bundle bundle = this.getArguments();
        // cars = (ArrayList<CarEntity>) bundle.getSerializable("cars");
        mListView = (ListView) view.findViewById(R.id.carsListView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), useremail);
        viewModel = new ViewModelProvider(this).get(CarListByOwnerViewModel.class);
        viewModel.getListCarByOwner().observe(getViewLifecycleOwner(), cars -> {
            if (cars != null) {

            }
        });


    }

    public void initVar() {
       String[] mCarsPlate = new String[1];
       int[] mCarsImage = new int[/*cars.size()*/1];
        mCarsPlate[0]="FR321372";
        mCarsImage[0]=R.drawable.iconecar;
        int i=0;
       /*   for (CarEntity car: cars) {
            mCarsPlate[i]=car.getPlate();
            mCarsImage[i]=R.drawable.iconecar;
            i=i+1;
        }*/
        CustomAdapter customAdapter = new CustomAdapter(getActivity().getApplicationContext(), mCarsPlate, mCarsImage);
        mListView.setAdapter(customAdapter);


    }

}
