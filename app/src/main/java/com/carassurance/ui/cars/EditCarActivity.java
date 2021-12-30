package com.carassurance.ui.cars;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.CarEntityF;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.AppActivity;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.report.ReportActivity;
import com.carassurance.util.OnAsyncEventListener;
import com.carassurance.viewmodel.CarViewModel;
import com.carassurance.viewmodel.CarViewModelF;
import com.carassurance.viewmodel.UserViewModel;
import com.carassurance.viewmodel.UserViewModelF;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Cette Class permet afficher les details d'une voiture. Elle permet également de modifier, supprimer et ajouté un véhicule
 */
public class EditCarActivity extends BaseActivity {
    private EditText txtplate;
    private EditText txtBrand;
    private EditText txtModele;
    private EditText txtColor;
    private Button btnEdit;
    private Button btnsave;
    private Button btnadd;
    private Button btndel;

    public boolean isAddOrEdit() {
        return AddOrEdit;
    }

    public void setAddOrEdit(boolean addOrEdit) {
        AddOrEdit = addOrEdit;
    }

    private CarViewModelF viewModel;
    public CarEntityF mCar;
    public boolean AddOrEdit;


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
        btnadd = findViewById(R.id.btn_addcar);
        btndel = findViewById(R.id.btn_del);
        btndel.setVisibility(View.INVISIBLE);
        btnsave.setVisibility(View.INVISIBLE);
        btnadd.setVisibility(View.INVISIBLE);
        btnEdit.setVisibility(View.INVISIBLE);
        Boolean type = getIntent().getBooleanExtra("value", true);

        AddOrEdit = type;
        if (AddOrEdit==true){
            btndel.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
            String idCar = getIntent().getExtras().getString("id");

            CarViewModelF.Factory factory = new CarViewModelF.Factory(getApplication(), idCar);
            viewModel = ViewModelProviders.of(this, factory).get(CarViewModelF.class);
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
                    btnsave.setVisibility(View.VISIBLE);
                    btndel.setVisibility(View.INVISIBLE);
                    btnEdit.setVisibility(View.INVISIBLE);
                }
            });
            btndel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.delete(mCar, new OnAsyncEventListener() {
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
            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCar.setColor(txtColor.getText().toString());
                    mCar.setBrand(txtBrand.getText().toString());
                    mCar.setPlate(txtplate.getText().toString());
                    mCar.setModel(txtModele.getText().toString());
                    viewModel.update(mCar, new OnAsyncEventListener() {
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
        if(AddOrEdit==false){
            btnadd.setVisibility(View.VISIBLE);
            CarViewModelF.Factory factory = new CarViewModelF.Factory(getApplication(),"");
            viewModel = ViewModelProviders.of(this, factory).get(CarViewModelF.class);
            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String color = txtColor.getText().toString();
                    String brand = txtBrand.getText().toString();
                    String plate = txtplate.getText().toString();
                    String model = txtModele.getText().toString();
                    if(color.isEmpty()){
                        txtColor.setError("Coleur non conforme");
                        txtColor.requestFocus();
                    }
                    if(brand.isEmpty()){
                        txtBrand.setError("marque non conforme");
                        txtBrand.requestFocus();
                    }
                    if(plate.isEmpty()){
                        txtplate.setError("plaque non conforme");
                        txtplate.requestFocus();
                    }
                    if(model.isEmpty()){
                        txtModele.setError("model non conforme");
                        txtModele.requestFocus();
                    }
                    if (!color.isEmpty() && !brand.isEmpty() && !plate.isEmpty() && !model.isEmpty()){
                        String owner;
                        UserViewModelF.Factory factory = new UserViewModelF.Factory(
                                getApplication(),
                                owner =  FirebaseAuth.getInstance().getCurrentUser().getUid()
                        );
                        CarEntityF c = new CarEntityF();
                        c.setColor(color);
                        c.setBrand(brand);
                        c.setPlate(plate);
                        c.setModel(model);
                        c.setOwner(owner);


                        viewModel.insert(c, new OnAsyncEventListener() {

                            @Override
                            public void onSuccess() {
                                if (getIntent().getExtras().getString("activity").equals("ui.report.ReportActivity"))
                                {
                                    goToReport();
                                }
                                else{
                                    goToCar();
                                }
                            }

                            @Override
                            public void onFailure(Exception e) {

                            }
                        });
                    }
                }
            });
        }
    }

    private void goToReport() {
        Intent i = new Intent( this , ReportActivity. class );
        startActivity(i);

    }

    /**
     * retourne vers l'activity principale
     */
    public void goToApp(){
        Intent i = new Intent( this , AppActivity. class );
        startActivity(i);
    }


    /**
     * retourne vers l'activity des voitures
     */
    public void goToCar(){
        Intent i = new Intent( this , CarsActivity. class );
        startActivity(i);
    }
}
