package com.carassurance.ui.report;

import static com.carassurance.R.color.red;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.report.fragments.IncidentTypeFragment;

import java.util.List;


public class ReportActivity extends BaseActivity {

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
}
