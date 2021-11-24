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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportDescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ReportVM viewModel;
    private EditText mDescription;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportDescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportDescriptionFragment newInstance(String param1, String param2) {
        ReportDescriptionFragment fragment = new ReportDescriptionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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