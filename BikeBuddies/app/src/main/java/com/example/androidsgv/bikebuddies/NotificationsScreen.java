package com.example.androidsgv.bikebuddies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by Sam on 4/21/2015.
 */
public class NotificationsScreen extends ActionBarActivity {

    ListView list;
    private Comparator<String[]> myComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_screen);
        // Make logo show up in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        //reset badge
        SharedPreferences preferencesBadge = this.getSharedPreferences("NotifyBadge", 0);
        SharedPreferences.Editor editorBadge = preferencesBadge.edit();
        editorBadge.putInt("numNotify", 0);
        editorBadge.commit();

        SharedPreferences preferences = getSharedPreferences("Notifications",0);

            Map<String,String> notificationEntries = (Map<String,String>) preferences.getAll();
            ArrayList<String[]> notify = new ArrayList<String[]>();

            for (Map.Entry<String,String> entry : notificationEntries.entrySet()) {
                String[] notifyLog = entry.getValue().split(",");
                Log.d("sam", "notifyLog is: " + notifyLog);
                Log.d("Sam", "notifylog length is: " + notifyLog.length + "");
                notify.add(notifyLog);

            }


            // Sort the notifications based on when they were added (reverse date order). The second
            //string takes care of this for us.
            Collections.sort(notify, new Comparator<String[]>() {
                @Override
                public int compare(String[] lhs, String[] rhs) {
                    //Log.d("Sam", "[0]= " + rhs[0] + "   and lhs[0]= " + lhs[0] + " ");
                    return rhs[0].compareTo(lhs[0]);
                }
            });

            LinearLayout layout = (LinearLayout) findViewById(R.id.notifications_layout);

            for (String[] row : notify) {
                View rowView = getRowViewForNotification(row);
                layout.addView(rowView);
            }

        }



    /*
    Take in a string array representing a ride; the entry is the string listing the friend
    who has accepted (or denied) the request to ride together
     */
        private View getRowViewForNotification(String[] notification) {
            String string = notification[0];

            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newView = inflater.inflate(R.layout.notification_row,null);

            TextView infoField = (TextView) newView.findViewById(R.id.name_of_other_user_notify);
            infoField.setText(string);

            return newView;
        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leaderboard_screen, menu);
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
