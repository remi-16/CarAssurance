package com.carassurance.ui.report.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.carassurance.R;
import com.carassurance.ui.report.ReportVM;


public class ReportDescriptionFragment extends Fragment {

    private ReportVM viewModel;
    private EditText mDescription;

    public ReportDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_report_description, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ReportVM.class);

        mDescription = result.findViewById(R.id.descriptionReport);
        if(viewModel.getDescription() != null){
            mDescription.setText(viewModel.getDescription());
        }

        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setDescription(s.toString());
            }
        });

        return result;
    }
}