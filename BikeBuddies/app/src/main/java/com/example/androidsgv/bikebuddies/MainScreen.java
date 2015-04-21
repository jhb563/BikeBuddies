package com.example.androidsgv.bikebuddies;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainScreen extends ActionBarActivity {

    TextView welcomeName;
    Button leaderboardButton;
    Button startRideButton;
    Button friendsButton;


    private static final int RIDE_CODE = 1;
    private static final String goToLeaderBoardKey = "GoToLeaderBoardKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        leaderboardButton = (Button) findViewById(R.id.leaderboardButton);
        startRideButton = (Button) findViewById(R.id.startRideButton);
        friendsButton = (Button) findViewById(R.id.findfriendsButton);
        welcomeName = (Button) findViewById(R.id.buttonName);
        String userName = String.format(getResources().getString(R.string.user_name),
                getResources().getString(R.string.THIS_USER) );
        welcomeName.setText(userName);


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
