package com.carassurance;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.carassurance.database.entity.UserEntity;
import com.carassurance.encryption.HashPassword;

import java.util.ArrayList;


public class LoginActivity extends BaseActivity{

    private EditText username;
    private EditText password;
    private Button login;
    private CheckBox stayConnect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getLayoutInflater().inflate(R.layout.activity_login, frameLayout);

        toggle.setDrawerIndicatorEnabled(false);

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

                /*Simulation DB users*/
                ArrayList<UserEntity> listUser = new ArrayList<>();
                HashPassword hashPassword = new HashPassword();
                listUser.add(new UserEntity("remi.cohu@gmail.com","Cohu","Rémi", hashPassword.hash("Soleil123")));

                boolean found = listUser.stream()
                        .anyMatch(p -> p.getEmail().equals(username.getText().toString()) && p.getPassword().equals((hashPassword.hash(password.getText().toString()))));

                if(found==true){
                    if (stayConnect.isActivated()){
                       // staylogged=2;
                    }
                    goToApp();
                    sp .edit().putBoolean( " logged " , true ).apply();
                }else{

                }
            }
        });

    }

    public void goToApp(){
        Intent i = new Intent( this , AppActivity. class );
        startActivity(i);
    }
}
