package com.carassurance.ui;


import static com.carassurance.database.AppDatabase.initializeData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.carassurance.BaseApp;
import com.carassurance.R;

import com.carassurance.database.entity.UserEntity;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.encryption.HashPassword;
import com.carassurance.viewmodel.UserViewModel;



public class LoginActivity extends BaseActivity{

    private EditText username;
    private EditText password;
    private Button login;
    private CheckBox stayConnect;
    private UserRepository repository;



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
        repository = ((BaseApp) getApplication()).getUserRepository();


      //  sp = getSharedPreferences( "login" , MODE_PRIVATE );

       /* if (sp.getBoolean("login")){
            staylogged =2;
            goToApp();
        }*/

        login .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initialUser(username.getText().toString());

            }
        });

    }

    public void initialUser(String email){
        HashPassword hashPassword = new HashPassword();


        repository.getUser(email, getApplication()).observe(LoginActivity.this, userEntity -> {
            if (userEntity != null) {
                user=userEntity;
                if(user.getPassword().equals(hashPassword.hash(password.getText().toString()))){


                    // We need an Editor object to make preference changes.
                    // All objects are from android.context.Context
                    SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
                    editor.putString(BaseActivity.PREFS_USER, userEntity.getEmail());
                    editor.apply();

                    username.setText("");
                    password.setText("");
                    goToApp();
                } else {
                    password.setError(getString(R.string.error_incorrect_password));
                    password.requestFocus();
                    password.setText("");
                }
            } else {
                username.setError(getString(R.string.error_invalid_email));
                username.requestFocus();
                password.setText("");

            }
        });

    }

    public void goToApp(){
        Intent i = new Intent( this , AppActivity. class );
        startActivity(i);
    }
}
