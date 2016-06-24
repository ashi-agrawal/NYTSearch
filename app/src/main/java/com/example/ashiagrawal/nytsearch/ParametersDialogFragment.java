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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    @BindView(R.id.spOrder)
    Spinner spOrder;
    @BindView(R.id.btnSave)
    Button save;
    @BindView(R.id.btnShowDate)
    Button showDate;
    @BindView(R.id.cbArts)
    CheckBox arts;
    @BindView(R.id.cbFashion)
    CheckBox fashion;
    @BindView(R.id.cbSports)
    CheckBox sports;
    private FilterInfo info;

    public ParametersDialogFragment() {
    }

    public static ParametersDialogFragment newInstance(FilterInfo filter) {
        ParametersDialogFragment frag = new ParametersDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("filters", filter);
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
                DatePickerFragment frag = new DatePickerFragment();
                frag.setTargetFragment(ParametersDialogFragment.this, 0);
                frag.show(fm, "DatePickerFragment");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        info = (FilterInfo) getArguments().getSerializable("filters");
        if (info.date != null) showDate.setText(info.getDateLabel());
        arts.setChecked(info.isArts());
        fashion.setChecked(info.isFashion());
        sports.setChecked(info.isSports());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.dropdown_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrder.setAdapter(adapter);
        spOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                info.setOrder((String)parent.getItemAtPosition(pos));
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        arts.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                onCheckBoxClicked(view);
            }
        });
        fashion.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                onCheckBoxClicked(view);
            }
        });
        sports.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                onCheckBoxClicked(view);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                OnFilterSearchListener listener = (OnFilterSearchListener) getActivity();
                listener.onUpdateFilters(info);
                dismiss();
            }
        });
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onCheckBoxClicked (View view) {
            boolean checked = ((CheckBox) view).isChecked();
            switch(view.getId()) {
                case R.id.cbArts:
                    info.setArts(checked);
                    break;
                case R.id.cbFashion:
                    info.setFashion(checked);
                    break;
                case R.id.cbSports:
                    info.setSports(checked);
                    break;
            }
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        showDate.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                + "-" + String.valueOf(dayOfMonth));
        info.date = Calendar.getInstance();
        info.date.set(Calendar.YEAR, year);
        info.date.set(Calendar.MONTH, monthOfYear);
        info.date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public interface OnFilterSearchListener {
        void onUpdateFilters(FilterInfo info);
    }
}

