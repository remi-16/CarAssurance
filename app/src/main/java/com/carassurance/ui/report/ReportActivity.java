package com.carassurance.ui.report;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.fragments.CarFragment;
import com.carassurance.ui.report.fragments.IncidentTypeFragment;


public class ReportActivity extends BaseActivity implements IncidentTypeFragment.OnButtonClickedListener, View.OnClickListener {

    private IncidentTypeFragment mIncidentTypeFragment;
    private CarFragment mCarFragment;
    private Button mCancelButton;
    private Button mNextButton;
    private Button mBackButton;
    private String mReportType;
    public String[] mIncidentTypes = {"Accident", "Brise de glace", "Dégat fouine", "Vol"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, IncidentTypeFragment.class, null)
                    .commit();
        }


        getLayoutInflater().inflate(R.layout.activity_report,frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);

        mCancelButton = findViewById(R.id.buttonCancel);
        mNextButton = findViewById(R.id.buttonNext);
        mBackButton = findViewById(R.id.buttonBack);

        mCancelButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

    }



    @Override
    public void onButtonClicked(View view, int selected) {

        mReportType = mIncidentTypes[selected];

    }

    @Override
    public void onClick(View view) {
        if (view == mNextButton) {
            //loadFragment(mCarFragment);
        } else if (view == mBackButton) {

        } else if (view == mCancelButton) {

        }
    }
}
