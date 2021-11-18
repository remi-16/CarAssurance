package com.carassurance.ui.cars.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.ui.incidents.EditIncidentActivity;
import com.carassurance.ui.incidents.IncidentsActivity;
import com.carassurance.ui.incidents.RecyclerAdapter;
import com.carassurance.viewmodel.CarListByOwnerViewModel;
import com.carassurance.viewmodel.CarListViewModel;

import java.util.ArrayList;
import java.util.List;



public class CarFragment extends Fragment {

    private View view;
    private List<CarEntity> mCars;
    private ArrayList<CarEntity> cars;
    private Button mButtou;
    private ListView mListView;
    private CarListViewModel viewModel;
    private UserRepository repository  ;
    private CustomAdapter customAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //      viewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(CarListByOwnerViewModel.class);
        view= inflater.inflate(R.layout.fragment_car, container, false);
        mButtou= (Button) view.findViewById(R.id.buttonAddCar);
        // Bundle bundle = this.getArguments();
        // cars = (ArrayList<CarEntity>) bundle.getSerializable("cars");
        mListView = (ListView) view.findViewById(R.id.carsListView);

        //  Bundle bundle = this.getArguments();
        // cars = (ArrayList<CarEntity>) bundle.getSerializable("cars");
        mListView = (ListView) view.findViewById(R.id.carsListView);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVar();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }




    public void initVar() {


        CarsActivity carsActivity = (CarsActivity)getActivity();
        this.mCars = carsActivity.mCars;
        String[] mCarsPlate = new String[mCars.size()];
        int[] mCarsImage =  new int[mCars.size()];
        int i=0;
          for (CarEntity car: mCars) {
            mCarsPlate[i]=car.getPlate();
            mCarsImage[i]=R.drawable.iconecar;
            i=i+1;
        }
        customAdapter = new CustomAdapter(getActivity(), getActivity().getApplicationContext(), mCarsPlate, mCarsImage);

        mListView.setAdapter(customAdapter);



    }

}