package com.carassurance;


import android.os.Bundle;

public class AppActivity extends BaseActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getLayoutInflater().inflate(R.layout.activity_app, frameLayout);

        toggle.setDrawerIndicatorEnabled(false);
    }
}
