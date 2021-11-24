package com.carassurance.ui.report;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.report.fragments.ReportIncidentTypeFragment;
import com.carassurance.ui.report.fragments.ReportDescriptionFragment;


public class ReportActivity extends BaseActivity implements View.OnClickListener {

    private ReportVM viewModel;

    private Fragment testFragment;

    private Button mCancelButton;
    private Button mNextButton;
    private Button mBackButton;
    private String mReportType;
    public String[] mIncidentTypes = {"Accident", "Brise de glace", "Dégat fouine", "Vol"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ReportVM.class);
        viewModel.getSelectedItem().observe(this, item -> {
            // Perform an action with the latest item data

        });


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, ReportIncidentTypeFragment.class, null)
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


/*
    @Override
    public void onButtonClicked(View view, int selected) {

        mReportType = mIncidentTypes[selected];

    }
*/

    @Override
    public void onClick(View view) {
        if (view == mNextButton) {

            if(viewModel.getCheckNext() == true){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("Test")
                            .replace(R.id.fragment_container_view, ReportDescriptionFragment.class, null)
                            .commit();

            }
        } else if (view == mBackButton) {
            getSupportFragmentManager().popBackStack();
        } else if (view == mCancelButton) {

        }
    }
}
