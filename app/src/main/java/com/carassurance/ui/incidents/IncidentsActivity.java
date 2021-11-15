package com.carassurance.ui.incidents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.ViewModelProviders;
import com.carassurance.R;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.ui.BaseActivity;
import com.carassurance.viewmodel.IncidentListByOwnerViewModel;

public class IncidentsActivity extends BaseActivity {

    private TextView textView;
    private List<IncidentEntity> mIncidents;
    private IncidentListByOwnerViewModel viewModel;
    private RecyclerAdapter<IncidentEntity> adapter;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_incidents, frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);
        textView = findViewById(R.id.IncidentsFixe);
        recyclerView = findViewById(R.id.contractsRecyclerView);


        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        mIncidents = new ArrayList<>();

        adapter = new RecyclerAdapter<IncidentEntity>((v, position) -> {
            Intent intent=new Intent(IncidentsActivity.this, EditIncidentActivity.class);

            Long incidentId = mIncidents.get(position).getId();
            intent.putExtra("incidentId",incidentId);
            startActivity(intent);
        });


        setupViewModels();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private void setupViewModels() {
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String useremail = settings.getString(PREFS_USER, null);
        IncidentListByOwnerViewModel.Factory factory = new IncidentListByOwnerViewModel.Factory(getApplication(), useremail);
        viewModel = ViewModelProviders.of(this, factory).get(IncidentListByOwnerViewModel.class);
        viewModel.getListByOwner().observe(this, incidents -> {
            if (incidents != null) {
                mIncidents = incidents;
                adapter.setData(mIncidents);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
