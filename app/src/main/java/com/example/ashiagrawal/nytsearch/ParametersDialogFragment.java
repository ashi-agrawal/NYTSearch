package com.example.ashiagrawal.nytsearch;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.ashiagrawal.nytsearch.activities.DatePickerFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ashiagrawal on 6/22/16.
 */
public class ParametersDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.spOrder) Spinner spOrder;
    @BindView(R.id.btnSave) Button save;
    @BindView(R.id.btnShowDate) Button showDate;

    public ParametersDialogFragment() {
    }


    public static ParametersDialogFragment newInstance() {
        ParametersDialogFragment frag = new ParametersDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parameters, container);
        ButterKnife.bind(this, view);
        showDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment frag = new DatePickerFragment().newInstance();
                frag.show(fm, "DatePickerFragment");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dropdown_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrder.setAdapter(adapter);
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                dismiss();
            }
        });
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        showDate.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                + "-" + String.valueOf(dayOfMonth));
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }
}

