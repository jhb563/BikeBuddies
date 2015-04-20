package com.example.androidsgv.bikebuddies;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MyRideHistoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ride_history);


        String[] ride3 = {"4/2/15","5.4 mi.","25:30"};
        String[] ride2 = {"4/10/15","6.3 mi.", "28:15"};
        String[] ride1 = {"4/15/15","4.3 mi.", "18:45"};

        String[][] rides = {ride1, ride2, ride3};

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
