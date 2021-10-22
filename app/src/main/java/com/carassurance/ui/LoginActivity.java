package com.carassurance.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.lifecycle.ViewModelProviders;

import com.carassurance.R;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.encryption.HashPassword;
import com.carassurance.viewmodel.UserViewModel;

import java.util.ArrayList;


public class LoginActivity extends BaseActivity{

    private EditText username;
    private EditText password;
    private Button login;
    private CheckBox stayConnect;
    private UserViewModel viewModel;
    private UserEntity user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_login, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.VISIBLE);

        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        login = findViewById(R.id.buttonLogin);
        stayConnect = findViewById(R.id.stayConnected);



      //  sp = getSharedPreferences( "login" , MODE_PRIVATE );

       /* if (sp.getBoolean("login")){
            staylogged =2;
            goToApp();
        }*/

        login .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initialUser(username.getText().toString());


                HashPassword hashPassword = new HashPassword();


                if(user.getPassword().equals(hashPassword.hash(password.getText().toString()))){
                    goToApp();
                }

            }
        });

    }

    public void initialUser(String email){
        UserViewModel.Factory factory = new UserViewModel.Factory(
                getApplication(), email);
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        viewModel.getUser().observe(this, userEntity -> {
            if (userEntity != null) {
                user = userEntity;
            }
        });
    }

    public void goToApp(){
        Intent i = new Intent( this , AppActivity. class );
        startActivity(i);
    }
}
