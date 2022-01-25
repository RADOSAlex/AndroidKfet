package fr.ensisa.rados.kfet.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String requestKey;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //DatePickerFragmentArgs arg = DatePickerFragmentArgs.fromBundle(getArguments());
        //requestKey = arg.getRequestKey();
        //Date date = new Date(arg.getDate());
        Calendar calendar = GregorianCalendar.getInstance();
       // calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), this, year, month, day);
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        long date = calendar.getTimeInMillis();
        Bundle result = new Bundle();
        result.putLong(requestKey, date);
        getParentFragmentManager().setFragmentResult(requestKey, result);
    }

}
