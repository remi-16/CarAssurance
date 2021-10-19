package com.carassurance.ui.cars;

import android.os.Bundle;
import android.view.View;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;

public class CarsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_cars, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);

    }
}
