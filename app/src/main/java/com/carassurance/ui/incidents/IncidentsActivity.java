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

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.ui.BaseActivity;

/**
 * Class principale de l'interface ou l'utilisateur peut voir ses incidents
 */
public class IncidentsActivity extends BaseActivity {

    private TextView textView;
    private List<IncidentWithCar> mIncidents;
    private RecyclerAdapter<IncidentWithCar> adapter;
    private RecyclerView recyclerView;
    private IncidentRepository repository;



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

        adapter = new RecyclerAdapter<IncidentWithCar>((v, position) -> {
            Intent intent=new Intent(IncidentsActivity.this, EditIncidentActivity.class);

            Long incidentId = mIncidents.get(position).incidents.getId();
            intent.putExtra("incidentId",incidentId);
            String plate = mIncidents.get(position).car.getPlate();
            intent.putExtra("plate",plate);
            startActivity(intent);
        });


        setupViewModels();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /**
     * Initialise le viewModel et transmet chaque incident de l'utilisateur dans une recycleView
     */
    private void setupViewModels() {
        SharedPreferences settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);
        String useremail = settings.getString(PREFS_USER, null);

        repository= ((BaseApp) getApplication()).getIncidentRepository();

        repository.getAllIncidentWithCar(useremail, getApplication()).observe(IncidentsActivity.this, incident -> {
            if (incident != null) {
                mIncidents = incident;
                adapter.setData(mIncidents);
                recyclerView.setAdapter(adapter);
            }

        });
    }
}
