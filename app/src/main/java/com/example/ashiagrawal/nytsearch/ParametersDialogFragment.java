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
    //DatePickerFragment.DatePickerListener
    @BindView(R.id.spOrder) Spinner spOrder;
    @BindView(R.id.btnSave) Button save;
    @BindView(R.id.btnShowDate) Button showDate;
    Calendar date;

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
        date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    //    public interface DatePickerListener {
//        void onFinishDatePicker(String inputText);
//    }
//
//    // Call this method to send the data back to the parent fragment
//    public void sendBackResult(int selectedMonth) {
//        DatePickerListener listener = (DatePickerListener) getTargetFragment();
//        listener.onFinishDatePicker(Integer.toString(selectedMonth));
//        dismiss();
//    }
//
//    public void onClick(DialogInterface dialog, int which) {
//        if(which==Dialog.BUTTON_POSITIVE)
//        {
//            sendBackResult(start.getMonth());
//        }
//
//    }
//
//    @BindView(R.id.dpStart) DatePicker start;
//    DatePickerDialog.OnDateSetListener onDateSet;
//    private boolean isModal = false;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if(isModal)
//        {
//            return super.onCreateView(inflater, container, savedInstanceState);
//        }
//        else {
//            View view = inflater.inflate(R.layout.fragment_date_picker, container);
//            ButterKnife.bind(this, view);
//            return view;
//        }
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
//        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, year, month, day);
//        start = dialog.getDatePicker();
//        start.updateDate(year, month, day);
//        return dialog;
//    }
//
//    public static DatePickerFragment newInstance()
//    {
//        DatePickerFragment frag = new DatePickerFragment();
//        frag.isModal = true;
//        return frag;
//    }
//
//    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
//        onDateSet = onDate;
//    }
//
//    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker view, int selectedYear,
//                              int selectedMonth, int selectedDay) {
//            Log.d("Date selected", String.valueOf(new StringBuilder().append(selectedMonth + 1)
//                    .append("-").append(selectedDay).append("-").append(selectedYear)
//                    .append(" ")));
//            sendBackResult(selectedMonth);
//        }
//    };
//}
}

