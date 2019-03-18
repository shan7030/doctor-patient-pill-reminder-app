package com.example.shantanu.samlple;

/**
 * Created by shantanu on 6/10/18.
 */
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment{
    Calendar c=Calendar
            .getInstance();
    int hour=c.get(Calendar.HOUR_OF_DAY);
    int min=c.get(Calendar.MINUTE);
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener)getActivity(),hour,min, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
