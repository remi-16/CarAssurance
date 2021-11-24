package com.carassurance.ui.report.fragments;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.carassurance.R;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.ui.report.ReportActivity;
import com.carassurance.ui.report.ReportVM;

import java.util.ArrayList;


public class IncidentTypeFragment extends Fragment implements View.OnClickListener {

    private ReportVM viewModel;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private String[] mIncidentTypes;

    //2 - Declare callback
    private OnButtonClickedListener mCallback;

    // 1 - Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        void onButtonClicked(View view, int selected);
    }

    // 3 - Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    public IncidentTypeFragment() {
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
        View result = inflater.inflate(R.layout.fragment_incident_type, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ReportVM.class);

        ReportActivity reportActivity = (ReportActivity) getActivity();
        this.mIncidentTypes = reportActivity.mIncidentTypes;

        initButtons(result);


        return result;
    }

    private void initButtons(View result) {
        mButton1 = result.findViewById(R.id.incidentTypeBtn1);
        mButton2 = result.findViewById(R.id.incidentTypeBtn2);
        mButton3 = result.findViewById(R.id.incidentTypeBtn3);
        mButton4 = result.findViewById(R.id.incidentTypeBtn4);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        mButton1.setText(mIncidentTypes[0]);
        mButton2.setText(mIncidentTypes[1]);
        mButton3.setText(mIncidentTypes[2]);
        mButton4.setText(mIncidentTypes[3]);

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
                Log.println(Log.INFO,"test","test : " + Integer.toString(i));
                viewModel.setCheckNext(true);

                // Check which radio button was clicked
                switch(radioGroup.getCheckedRadioButtonId()) {
                    case R.id.incidentTypeRB1:
                            viewModel.setType(mIncidentTypes[0]);
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




    @Override
    public void onClick(View v) {
        int selected = -1;


        if (v == mButton1) {
            selected = 0;
            viewModel.setType(mIncidentTypes[0]);
        } else if (v == mButton2) {
            selected = 1;
            viewModel.setType(mIncidentTypes[1]);
        } else if (v == mButton3) {
            selected = 2;
            viewModel.setType(mIncidentTypes[2]);
        } else if (v == mButton4) {
            selected = 3;
            viewModel.setType(mIncidentTypes[3]);
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        // 5 - Spread the click to the parent activity
        mCallback.onButtonClicked(v,selected);
    }
}