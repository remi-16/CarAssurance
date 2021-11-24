package com.carassurance.ui;

import android.content.Intent;
import android.os.Bundle;

import com.carassurance.R;
import com.carassurance.ui.report.ReportActivity;


public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setTitle(getString(R.string.title_activity_main));

        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        Intent intent = null;

        //Pour test Matt
        //intent = new Intent(this, ReportActivity.class);

        intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


}