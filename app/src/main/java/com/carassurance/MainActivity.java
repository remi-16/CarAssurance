package com.carassurance;

import android.os.Bundle;
import android.view.Menu;


public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setTitle(getString(R.string.title_activity_main));
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
    }


}