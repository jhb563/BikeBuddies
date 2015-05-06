package com.example.androidsgv.bikebuddies;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * This class displays the user's personal history of rides accomplished and recorded, displaying
 * the date, length and distance of each ride, with the most recent displayed first.
 *
 * Created by James Bowen.
 */


public class MyRideHistoryActivity extends ActionBarActivity {

    private Comparator<String[]> myComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ride_history);
        // Make logo show up in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        SharedPreferences preferences = getSharedPreferences("Ride History",0);

        /******************************
         *  THE 3 LINES BELOW CLEAR OUT THE SHARED PREFERENCES FILE. UNCOMMENT THIS CODE
         *  ONCE IF YOU WANT TO REMOVE ANY PREVIOUS RIDE ENTRIES.
         ******************************/
//        SharedPreferences.Editor edit = preferences.edit();
//        edit.clear();
//        edit.commit();

        Map<String,String> rideEntries = (Map<String,String>) preferences.getAll();
        ArrayList<String[]> rides = new ArrayList<String[]>();

        for (Map.Entry<String,String> entry : rideEntries.entrySet()) {
            String[] rideLog = entry.getValue().split(",");
            Log.d("James", rideLog.length + "");
            if (rideLog.length >= 4) {
                rides.add(rideLog);
            } else {
                Log.d("James",entry.getValue());
            }

        }

        // Sort the rides based on when they were added (reverse date order). The fourth string
        // in each ride array keeps track of this.
        Collections.sort(rides,new Comparator<String[]>() {
            @Override
            public int compare(String[] lhs, String[] rhs) {
                return rhs[3].compareTo(lhs[3]);
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.ride_history_layout);

        for (String[] row : rides) {
            View rowView = getRowViewForRide(row);
            layout.addView(rowView);
        }

    }



    /*
    Take in a string array representing a ride
    - The first entry is the date
    - The second entry is the distance
    - The third entry is the time
     */
    private View getRowViewForRide(String[] ride) {
        String date = ride[0];
        String distance = ride[1];
        String time = ride[2];

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.ride_history_row,null);

        TextView dateField = (TextView) newView.findViewById(R.id.ride_date);
        dateField.setText(date);

        TextView distanceField = (TextView) newView.findViewById(R.id.ride_distance);
        distanceField.setText(distance);

        TextView timeField = (TextView) newView.findViewById(R.id.ride_time);
        timeField.setText(time);


        return newView;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_ride_history, menu);
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
}
