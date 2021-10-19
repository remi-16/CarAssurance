package com.carassurance.ui.report;

import android.os.Bundle;
import android.view.View;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;

public class ReportActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_report, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);

    }
}
