package com.carassurance.ui.report;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.carassurance.BaseApp;
import com.carassurance.R;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.repository.UserRepository;
import com.carassurance.ui.AppActivity;
import com.carassurance.ui.BaseActivity;
import com.carassurance.ui.cars.fragments.CarFragment;
import com.carassurance.ui.report.fragments.ReportDescriptionFragment;
import com.carassurance.ui.report.fragments.ReportIncidentTypeFragment;
import com.carassurance.util.OnAsyncEventListener;
import com.carassurance.viewmodel.IncidentViewModel;

import java.util.Calendar;
import java.util.Date;

/**
    Cette activité permet l'ajout d'un nouvel incident en se basant sur plusieurs fragments.
    Les boutons Suivant, Retour et Annuler sont liés à l'activité et non aux différents fragments.
    La gestion des prochains et prédécents fragments est gérée ici.
    La communication des données se fait via la classe ReportVM qui permet à chaque fragments d'avoir accès au même viewModel.
    Une fois les données entrées, l'ajout à la base de donnée se fait via les données du viewModel et d'autres générées.
 */
public class ReportActivity extends BaseActivity implements View.OnClickListener {

    public ReportVM viewModel;
    private SharedPreferences settings;
    private UserRepository mUserRepository;
    private IncidentViewModel mIncidentViewModel;
    private Button mCancelButton;
    private Button mNextButton;
    private Button mBackButton;
    public String[] mIncidentTypes = {"Accident", "Brise de glace", "Dégat fouine", "Vol"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ReportVM.class);
        viewModel.getSelectedItem().observe(this, item -> {
        });

        mUserRepository = ((BaseApp) getApplication()).getUserRepository();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, ReportIncidentTypeFragment.class, null)
                    .commit();
        }

        getLayoutInflater().inflate(R.layout.activity_report,frameLayout);
        toggle.setDrawerIndicatorEnabled(false);
        mUrgencyLayout.setVisibility(View.GONE);

        mCancelButton = findViewById(R.id.buttonCancel);
        mNextButton = findViewById(R.id.buttonNext);
        mBackButton = findViewById(R.id.buttonBack);

        mCancelButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        IncidentViewModel.Factory factory = new IncidentViewModel.Factory(getApplication(), null);
        mIncidentViewModel = ViewModelProviders.of(this, factory).get(IncidentViewModel.class);

        settings = getSharedPreferences(BaseActivity.PREFS_NAME, 0);

        String useremail = settings.getString(PREFS_USER, null);

        mUserRepository.getAllCarByOwner(useremail, getApplication()).observe(this, userEntity -> {
            if (userEntity != null) {
                mCars = userEntity.get(0).cars;
            }
        });
    }

    /**
        Gestion des boutons au bas de l'écran. Le bouton suivant est verrouillé si les informations n'ont pas été entrées.
        L'utilisateur peut revenir au fragment précédent et inversément en conservant ses données grâce au popBackStack().
     */
    @Override
    public void onClick(View view) {
        if (view == mNextButton) {
            switch (viewModel.getActualFragment()){
                case 1:
                    if(viewModel.getType() !=  null) {
                        viewModel.setActualFragment(2);
                        replaceFragment(CarFragment.class);
                    }
                    break;
                case 2:
                    if(viewModel.getCar_id() != null){
                        viewModel.setActualFragment(3);
                        mNextButton.setText("Enregistrer");
                        replaceFragment(ReportDescriptionFragment.class);
                    }
                    break;
                case 3:
                    insertIncident();
            }
        } else if (view == mBackButton) {
            mNextButton.setText("Suivant");
            viewModel.setActualFragment(viewModel.getActualFragment() -1);
            getSupportFragmentManager().popBackStack();

        } else if (view == mCancelButton) {
            Intent i = new Intent( this , AppActivity. class );
            startActivity(i);
        }
    }

    /**
     * Remplace le fragment actuel en fonction de celui demandé par la méthode onClick()
     */
    private void replaceFragment(Class fragment){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack(fragment.toString())
                .replace(R.id.fragment_container_view, fragment, null)
                .commit();
    }

    /**
     * Récupération user, génération date du jour et ajout de l'incident via les données du viewModel enregistrées par les fragments
     */
    private void insertIncident(){
        String useremail = settings.getString(PREFS_USER, null);
        Date date = Calendar.getInstance().getTime();


        IncidentEntity incidentEntity = new IncidentEntity(useremail,viewModel.getCar_id(),"test", DateFormat.format("dd.MM.yyyy", date).toString(),viewModel.getType(),viewModel.getInjured(),"",viewModel.getDescription());
        mIncidentViewModel.create(incidentEntity, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                confirmationPopup(incidentEntity);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /**
     * Popup pour confirmer à l'utilisateur la sauvegarde de son incident avec un bref résumé
     * @param incidentEntity
     */
    private void confirmationPopup(IncidentEntity incidentEntity){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Résumé")
                .setMessage("Type d'incident : " + incidentEntity.getType() + "\nBlessés : " + incidentEntity.getInjured() + "\nDate : " + incidentEntity.getDate() + "\nDescription : " + viewModel.getDescription())
                .setPositiveButton("Ok", (dialog, which) -> finish())
                .create()
                .show();
    }
}
