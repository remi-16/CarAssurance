package com.carassurance.ui.incidents;

import android.os.Bundle;
import android.view.View;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;

public class IncidentsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_incidents, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);

    }
}
