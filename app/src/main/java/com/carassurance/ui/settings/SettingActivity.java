package com.carassurance.ui.settings;

import android.os.Bundle;
import android.view.View;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().add(R.id.main_frame_layout, new SettingsFragment()).commit();
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {

    }
}