package com.carassurance.ui.settings;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.carassurance.R;
import com.carassurance.ui.BaseActivity;

public class SettingsFragment  extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // below line is used to add preference
        // fragment from our xml folder.
        addPreferencesFromResource(R.xml.preferences);
    }

    /**
     * Change the mode when the user click on it
     * Change the language when the user choose one
     *
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


        //change mode
        if (key.equals("theme_mode")) {


            boolean modePref = sPref.getBoolean("theme_mode", true);
            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.themeMode= modePref;
            if (modePref) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

        }


    }



    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

}