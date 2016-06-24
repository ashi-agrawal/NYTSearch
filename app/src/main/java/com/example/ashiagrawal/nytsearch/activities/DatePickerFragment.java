package com.example.ashiagrawal.nytsearch.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by ashiagrawal on 6/23/16.
 */
public class DatePickerFragment extends android.support.v4.app.DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
}

