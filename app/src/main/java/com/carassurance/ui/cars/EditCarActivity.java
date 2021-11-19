package com.carassurance.ui.cars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.AppActivity;
import com.carassurance.ui.BaseActivity;
import com.carassurance.util.OnAsyncEventListener;
import com.carassurance.viewmodel.CarViewModel;
import com.carassurance.viewmodel.UserViewModel;

import java.util.List;

public class EditCarActivity extends BaseActivity {
    private EditText txtplate;
    private EditText txtBrand;
    private EditText txtModele;
    private EditText txtColor;
    private Button btnEdit;
    private Button btnsave;
    private CarViewModel viewModel;
    public CarEntity mCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_editcar, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);
        txtColor = findViewById(R.id.txt_color);
        txtModele = findViewById(R.id.txt_modele);
        txtplate = findViewById(R.id.txt_plate);
        txtBrand = findViewById(R.id.txt_brand);
        btnEdit = findViewById(R.id.btn_editcar);
        btnsave = findViewById(R.id.btn_savecar);


        String plate = getIntent().getExtras().getString("plate");

        CarViewModel.Factory factory = new CarViewModel.Factory(getApplication(), plate);
        viewModel = ViewModelProviders.of(this, factory).get(CarViewModel.class);
        viewModel.getCar().observe(this, carEntity -> {
            if (carEntity != null) {
                mCar = carEntity;
                txtplate.setText(mCar.getPlate());
                txtBrand.setText(mCar.getBrand());
                txtModele.setText(mCar.getModel());
                txtColor.setText(mCar.getColor());
                txtplate.setFocusable(false);
                txtColor.setFocusable(false);
                txtModele.setFocusable(false);
                txtBrand.setFocusable(false);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtplate.setFocusableInTouchMode(true);
                txtColor.setFocusableInTouchMode(true);
                txtModele.setFocusableInTouchMode(true);
                txtBrand.setFocusableInTouchMode(true);
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCar.setColor(String.valueOf(txtColor.getText().toString()));
                mCar.setBrand(String.valueOf(txtBrand.getText().toString()));
                mCar.setPlate(String.valueOf(txtplate.getText().toString()));
                mCar.setModel(String.valueOf(txtModele.getText().toString()));
                viewModel.updateCar(mCar, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        goToApp();
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

        });
    }
    public void goToApp(){
        Intent i = new Intent( this , AppActivity. class );
        startActivity(i);
    }
}
