package com.carassurance.ui;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.carassurance.R;
import com.carassurance.database.entity.CarEntity;
import com.carassurance.database.entity.UserEntity;
import com.carassurance.ui.settings.AboutUsActivity;
import com.carassurance.ui.settings.SettingActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public abstract class BaseActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    /**
     * Class abstrait qui initialise un affichage standart pour les autres activitées
     */

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";
    protected SharedPreferences sp ;
    protected FrameLayout frameLayout;
    protected ActionBarDrawerToggle toggle;
    protected ConstraintLayout mUrgencyLayout;
    protected UserEntity user;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    public List<CarEntity> mCars;
    public Boolean themeMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(this);
        themeMode = shPref.getBoolean("theme_mode", false);

        if (themeMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        /*----------------------Hooks----------------------*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.nav_view);

        /*----------------------Toolbar----------------------*/
        setSupportActionBar(mToolbar);

        /*----------------------Nav_View----------------------*/
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*-------------------UrgencyLayout--------------------*/
        mUrgencyLayout = (ConstraintLayout) findViewById(R.id.urgency_layout);
        mUrgencyLayout.setVisibility(View.VISIBLE);


        toggle.setDrawerIndicatorEnabled(false);

        toggle.syncState();
        frameLayout = findViewById(R.id.main_frame_layout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Ouvre la navigation lorsque l'on clic sur le roulette des parametres
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (this.getLocalClassName().equals("ui.LoginActivity")){

        }else{
            if (menuItem.getItemId() == R.id.setting) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(menuItem);
    }

    /**
     * Navigue dans setting ou about us
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        Log.d("TAG", String.valueOf(item.getItemId()));
        switch (item.getItemId()) {
            case R.id.tb_settings:
                intent = new Intent(this, SettingActivity.class);
                break;
            case R.id.tb_about:
                intent = new Intent(this, AboutUsActivity.class);
                break;
            case R.id.tb_quit:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.tb_menu:
                intent = new Intent(this, AppActivity.class);
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }



}
