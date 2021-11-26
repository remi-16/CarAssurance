package com.carassurance.ui.cars.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.EditCarActivity;
import com.carassurance.viewmodel.CarListViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Cette classe est le Fagment contenant la liste de voitures de l'utlisateurs
 */
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
        view= inflater.inflate(R.layout.fragment_car, container, false);
        mButtou= (Button) view.findViewById(R.id.buttonAddCar);
        mButtou .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditCarActivity. class );
                i.putExtra("value",false);

                getActivity().startActivity(i);
            }
        });

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

    public void goToEditCar(){
        Intent i = new Intent( getActivity() , EditCarActivity. class );
        startActivity(i);
    }

    /**
     * cette méthode récupère les voitures et les envoie au CustomAdapter afin de créé une liste personnalisé
     */
    public void initVar() {

        BaseActivity carsActivity = (BaseActivity) getActivity();
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