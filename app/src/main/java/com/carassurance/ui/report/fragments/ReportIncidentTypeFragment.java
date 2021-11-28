package com.carassurance.ui.report.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.carassurance.R;
import com.carassurance.ui.report.ReportActivity;
import com.carassurance.ui.report.ReportVM;

/**
 * Choix du type d'incident. En cas d'accident, un popup demande à l'utilisateur s'il y a des blessés.
 * Le bouton suivant est verrouillé tant qu'un choix n'a pas été fait (via l'activité)
 */
public class ReportIncidentTypeFragment extends Fragment {

    private ReportVM viewModel;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private String[] mIncidentTypes;

    public ReportIncidentTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_report_incident_type, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ReportVM.class);
        viewModel.setActualFragment(1);

        ReportActivity reportActivity = (ReportActivity) getActivity();
        this.mIncidentTypes = reportActivity.mIncidentTypes;

        initButtons(result);

        return result;
    }

    /**
     * Initialisation des radioButtons et gestion du listener
     * @param result
     */
    private void initButtons(View result) {

        mRadioGroup = result.findViewById(R.id.incidentTypeRadioGroup);
        mRadioButton1 = result.findViewById(R.id.incidentTypeRB1);
        mRadioButton2 = result.findViewById(R.id.incidentTypeRB2);
        mRadioButton3 = result.findViewById(R.id.incidentTypeRB3);
        mRadioButton4 = result.findViewById(R.id.incidentTypeRB4);

        mRadioButton1.setText(mIncidentTypes[0]);
        mRadioButton2.setText(mIncidentTypes[1]);
        mRadioButton3.setText(mIncidentTypes[2]);
        mRadioButton4.setText(mIncidentTypes[3]);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                viewModel.setInjured(false);

                switch(radioGroup.getCheckedRadioButtonId()) {
                    case R.id.incidentTypeRB1:
                            viewModel.setType(mIncidentTypes[0]);
                            injuryPopup();
                        break;
                    case R.id.incidentTypeRB2:
                            viewModel.setType(mIncidentTypes[1]);
                        break;
                    case R.id.incidentTypeRB3:
                            viewModel.setType(mIncidentTypes[2]);
                        break;
                    case R.id.incidentTypeRB4:
                            viewModel.setType(mIncidentTypes[3]);
                        break;
                }
            }
        });
    }

    /**
     * Popup demandant s'il y a eu des blessés ou non
     */
    private void injuryPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Accident")
                .setMessage("Y a-t'il eu des blessés?")
                .setPositiveButton("Oui", (dialog, which) -> viewModel.setInjured(true))
                .setNegativeButton("Non", (dialog, which) -> viewModel.setInjured(false))
                .create()
                .show();
    }
}