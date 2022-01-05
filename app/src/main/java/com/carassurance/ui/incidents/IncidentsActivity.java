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
import com.carassurance.database.pojo.IncidentWithCarF;
import com.carassurance.database.repository.IncidentRepository;
import com.carassurance.database.repository.IncidentRepositoryF;
import com.carassurance.ui.BaseActivity;
import com.carassurance.viewmodel.UserViewModelF;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Class principale de l'interface ou l'utilisateur peut voir ses incidents
 */
public class IncidentsActivity extends BaseActivity {

    private TextView textView;
    private List<IncidentWithCarF> mIncidents;
    private RecyclerAdapter<IncidentWithCarF> adapter;
    private RecyclerView recyclerView;
    private IncidentRepositoryF repository;



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

        adapter = new RecyclerAdapter<IncidentWithCarF>((v, position) -> {
            Intent intent=new Intent(IncidentsActivity.this, EditIncidentActivity.class);

            String incidentId = mIncidents.get(position).incidents.getId();
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
        String userID;

        UserViewModelF.Factory factory = new UserViewModelF.Factory(
                getApplication(),
                userID = FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

        repository= ((BaseApp) getApplication()).getIncidentRepositoryF();

        repository.getAllIncidentWithCar(userID).observe(IncidentsActivity.this, incident -> {
            if (incident != null) {
                mIncidents = incident;
                adapter.setData(mIncidents);
                recyclerView.setAdapter(adapter);
            }

        });
    }
}
