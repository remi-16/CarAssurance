package com.carassurance.ui.report.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.carassurance.R;


public class IncidentTypeFragment extends Fragment implements View.OnClickListener {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    //2 - Declare callback
    private OnButtonClickedListener mCallback;

    // 1 - Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        public void onButtonClicked(View view, int selected);
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



        mButton1 = result.findViewById(R.id.incidentTypeBtn1);
        mButton2 = result.findViewById(R.id.incidentTypeBtn2);
        mButton3 = result.findViewById(R.id.incidentTypeBtn3);
        mButton4 = result.findViewById(R.id.incidentTypeBtn4);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        return result;
    }

    @Override
    public void onClick(View v) {
        int selected;

        if (v == mButton1) {
            selected = 0;
        } else if (v == mButton2) {
            selected = 1;
        } else if (v == mButton3) {
            selected = 2;
        } else if (v == mButton4) {
            selected = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        // 5 - Spread the click to the parent activity
        mCallback.onButtonClicked(v,selected);
    }
}