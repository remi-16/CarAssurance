package com.carassurance.ui;

import android.content.Intent;
import android.os.Bundle;

import com.carassurance.R;


public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setTitle(getString(R.string.title_activity_main));

        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        Intent intent = null;
        intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


}