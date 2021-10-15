package com.carassurance;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.carassurance.encryption.HashPassword;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected FrameLayout frameLayout;
    protected boolean urgency = true;
    private ConstraintLayout mUrgencyLayout;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        /*----------------------Hooks----------------------*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView = findViewById(R.id.nav_view);

        /*----------------------Toolbar----------------------*/
        setSupportActionBar(mToolbar);

        /*----------------------Nav_View----------------------*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        /*-------------------UrgencyLayout--------------------*/
        mUrgencyLayout = (ConstraintLayout) findViewById(R.id.urgency_layout);

        if (urgency == true) {
            mUrgencyLayout.setVisibility(View.VISIBLE);
        }else{
            mUrgencyLayout.setVisibility(View.GONE);
        }
        HashPassword hash = new HashPassword();
        hash.hash("passord1234");

    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.setting) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }


}
