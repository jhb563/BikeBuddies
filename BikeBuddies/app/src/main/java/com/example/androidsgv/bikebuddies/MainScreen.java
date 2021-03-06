package com.example.androidsgv.bikebuddies;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * This class serves the main screen of the application, and includes setting the notifications
 * badge information and the functions that start other activities when the corresponding button
 * has been selected.
 */

public class MainScreen extends ActionBarActivity {

    Button notifyButton;
    ImageButton leaderboardButton;
    ImageButton startRideButton;
    ImageButton friendsButton;
    TextView badgeNumber;


    private static final int RIDE_CODE = 1;
    private static final String goToLeaderBoardKey = "GoToLeaderBoardKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        // Make logo show up in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //set various layout pieces for clicking/changing as necessary
        leaderboardButton = (ImageButton) findViewById(R.id.leaderboardButton);
        startRideButton = (ImageButton) findViewById(R.id.startRideButton);
        friendsButton = (ImageButton) findViewById(R.id.findfriendsButton);
        notifyButton = (Button) findViewById(R.id.buttonNotify);
        badgeNumber = (TextView) findViewById(R.id.badgeText);
        int seen = 0;
        Log.d("Sam", "has been here" +Integer.toString(seen));
        setNotifyTextView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotifyTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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

    //get the accurate value for number of unseen notifications
    public void setNotifyTextView(){
        badgeNumber = (TextView) findViewById(R.id.badgeText);
        SharedPreferences preferencesNotify = getSharedPreferences("NotifyBadge",0);
        //77 means something went wrong and we couldn't get the real value
        int value = preferencesNotify.getInt("numNotify", 77);
        Log.d("SAM", "value is: " + Integer.toString(value));
        badgeNumber.setText(Integer.toString(value));
        Log.d("SAM", "value is after: " + badgeNumber.getText());

    }


    /*The following set of functions create intents and start Activities based on
    * whichever button has been selected. These are called when buttons are clicked
    * as set in activity_main_screen.xml */


     public void startRide(View v) {

        Intent intent = new Intent(this,RideScreen.class);
        startActivityForResult(intent,RIDE_CODE);

    }

    public void goToLeaderboard(View v) {
        Intent intent = new Intent(this,LeaderboardScreen.class);
        startActivity(intent);
    }

    public void findFriends(View v) {
        Intent intent = new Intent(this,FindFriends.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RIDE_CODE && resultCode == RIDE_CODE) {
            Intent newIntent = new Intent(this,LeaderboardScreen.class);
            startActivity(newIntent);
        }
    }

    public void openMyHistory(View v) {
        Intent intent = new Intent(this,MyRideHistoryActivity.class);
        startActivity(intent);
    }

    public void seeNotifications(View v){
        Intent intent = new Intent(this, NotificationsScreen.class);
        startActivity(intent);
    }
}
