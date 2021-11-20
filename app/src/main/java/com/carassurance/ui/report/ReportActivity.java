package com.carassurance.ui.report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.report.fragments.IncidentTypeFragment;


public class ReportActivity extends BaseActivity implements IncidentTypeFragment.OnButtonClickedListener {

    private Button mCancelButton;
    private Button mNextButton;
    private Button mBackButton;



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

    }

    @Override
    public void onButtonClicked(View view, int selected) {

        Log.d("D", String.valueOf(selected));

    }
}
