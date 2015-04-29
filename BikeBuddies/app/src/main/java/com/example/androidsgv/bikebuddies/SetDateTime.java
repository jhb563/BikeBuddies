package com.example.androidsgv.bikebuddies;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class SetDateTime extends ActionBarActivity {

    private static int FINISH_CODE = 2;
    private static String finishString = "Finish";
    private int mSelectedYear;
    private int mSelectedMonth;
    private int mSelectedDate;
    private int mSelectedHour;
    private int mSelectedSecond;
    private Bundle extras;
    public static String friendName = "no name";
    public static String dateToPrint;
    public static String timeToPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date_time);
        // Make logo show up in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        EditText textBox = (EditText) findViewById(R.id.EditTextFeedbackBody);
        textBox.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //set up notification in case we actually do send the request to a friend
        extras = getIntent().getExtras();
        if (extras ==null){
            friendName = "Friend";
        } else{
            friendName = ( extras.getString("name_of_friend"));
            Log.d("Sam", "my friend name is: " + friendName);
        }
    }

    public String getFriendName(){
        return friendName;
    }

    public void setDate(int y, int m, int d) {
        mSelectedYear = y;
        mSelectedMonth = m;
        mSelectedDate = d;
        Button date = (Button) findViewById(R.id.setDate);
        date.setText("Date: " + Integer.toString(m+1) + "/" + Integer.toString(d) +"/" + Integer.toString(y));
        this.dateToPrint = Integer.toString(m+1) + "/" + Integer.toString(d) +"/" + Integer.toString(y);
    }

    public void setTime(int h, int s) {
        String amPM = " AM";
        if (h==0) {
            h = 12;
        }
        else if (h >= 12) {
            if (h > 12)
                h = h - 12;
            amPM = " PM";
        }
        mSelectedHour = h;
        mSelectedSecond = s;
        String sToPrint = Integer.toString(s);
        if (sToPrint.length() == 1){
            sToPrint = "0" + sToPrint;
        }
        Button time = (Button) findViewById(R.id.setTime);
        time.setText("Time: " + h + ":" + sToPrint + amPM);
        this.timeToPrint = h + ":" + sToPrint + amPM;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_date_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            SetDateTime thisActivity = (SetDateTime) getActivity();
            thisActivity.setTime(hourOfDay,minute);
        }
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            SetDateTime thisActivity = (SetDateTime) getActivity();
            thisActivity.setDate(year,month,day);
        }
    }

    public static class AlertDialogFragment extends DialogFragment {
        @Override
        // Creates the yes no dialog
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.alert_message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES! Or, you know, confirm that yes, the user does
                            // want to do this
                            DialogFragment newFragment = new confirmDialogFragment();
                            newFragment.show(getFragmentManager(), "confirmed");
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }



    public static class confirmDialogFragment extends DialogFragment {
        @Override
        // creates the confirmation dialog fragment so that the user goes back to the home screen
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.confirm)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();

                            //store the request as an accepted notification in SharedPreferences
                            SharedPreferences preferences = getActivity().getSharedPreferences("Notifications", 0);
                            SharedPreferences.Editor editor = preferences.edit();


                            final Calendar c = Calendar.getInstance();
                            String dateTimeString = c.toString();
                            Log.d("calendar", "dateTimeString is: " + dateTimeString);
                            String printing = SetDateTime.friendName + " has accepted your request for "
                                    + SetDateTime.dateToPrint + " at " + SetDateTime.timeToPrint;
                            //+"has accepted your request!"+ "," + "!" +","
                            String finalString = printing + ","  + dateTimeString;
                            Log.d("Sam", "here's what we saved: " + finalString);
                            editor.putString(dateTimeString,finalString);
                            editor.commit();

                            //store the notification in our notification counter for the main screen badge
                            SharedPreferences preferencesBadge = getActivity().getSharedPreferences("NotifyBadge", 0);
                            SharedPreferences.Editor editorBadge = preferencesBadge.edit();
                            //42 means something went wrong and we couldn't get the real value
                            int valueToChange = preferencesBadge.getInt("numNotify", 42);
                            valueToChange += 1;
                            Log.d("Sam value", Integer.toString(valueToChange));
                            editorBadge.putInt("numNotify", valueToChange);
                            editorBadge.commit();

                            //transition to main screen
                            Intent newIntent = new Intent();
                            newIntent.putExtra(finishString,true);
                            getActivity().setResult(FINISH_CODE,newIntent);
                            getActivity().finish();

                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

    }

    public void yesNoAlert(View view) {
        DialogFragment newFragment = new AlertDialogFragment();
        newFragment.show(getFragmentManager(), "alert");
    }


}




