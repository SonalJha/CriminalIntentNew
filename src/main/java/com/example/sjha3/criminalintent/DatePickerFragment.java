package com.example.sjha3.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by sjha3 on 6/19/16.
 */


public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE="com.sjha3.andoird.criminalIntent.date";
    private Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // retrieving the argument that i set on newInstance
        mDate= (Date) getArguments().getSerializable(EXTRA_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year= calendar.get(Calendar.YEAR);
        int month= calendar.get(Calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);

        DatePicker datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                //translate year month and day to date using a calendar
                mDate= new GregorianCalendar(year,month,day).getTime();

                //update arguments to preserve selected values on rotation
                // we could have used retain fragment as well but for Dialog fragment currently has a bug with it
                getArguments().putSerializable(EXTRA_DATE,mDate);

            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title).setPositiveButton((android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendResult(Activity.RESULT_OK);
            }
        }).create();
    }

    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args= new Bundle();
        args.putSerializable(EXTRA_DATE,date);
        DatePickerFragment datePickerFragment= new DatePickerFragment();
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    // we create a function ourseleves send result which will send the result
    // we call this function when return the dialog, we replace null by a onclick listener and call this method
    // we pass RESULT_OK as the result code
    public void sendResult(int resultCode)
    {
        if(getTargetFragment()==null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE,mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);
    }

}
