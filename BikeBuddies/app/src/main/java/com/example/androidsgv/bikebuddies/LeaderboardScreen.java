package com.example.androidsgv.bikebuddies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class LeaderboardScreen extends ActionBarActivity {

    ListView list;
    String[] people ={
            "Hannah Y.",
            "Jim B.",
            "James B.",
            "Vanessa R.",
            "George S."
    };
    String[] data ={
            "04:05:10 7.5mi",
            "03:04:12 2.2mi",
            "03:01:01 2.1mi",
            "02:34:19 1.8mi",
            "00:00:00 0.0mi"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_screen);

        ClassLeaderboardCustomAdapter adapter=new ClassLeaderboardCustomAdapter(this, people, data);
        list=(ListView)findViewById(R.id.listViewForLeaderboard);
        list.setAdapter(adapter);
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
