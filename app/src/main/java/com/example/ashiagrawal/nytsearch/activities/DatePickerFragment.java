package com.example.ashiagrawal.nytsearch.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.ashiagrawal.nytsearch.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ashiagrawal on 6/23/16.
 */
public class DatePickerFragment extends android.support.v4.app.DialogFragment
        {

    @BindView(R.id.dpStart) DatePicker start;
    DatePickerDialog.OnDateSetListener onDateSet;
    private boolean isModal = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(isModal)
        {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        else {
            View view = inflater.inflate(R.layout.fragment_date_picker, container);
            ButterKnife.bind(this, view);
            return view;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Activity needs to implement this interface
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getParentFragment();
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

    public static DatePickerFragment newInstance()
    {
        DatePickerFragment frag = new DatePickerFragment();
        frag.isModal = true;
        return frag;
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        onDateSet = onDate;
    }
}
