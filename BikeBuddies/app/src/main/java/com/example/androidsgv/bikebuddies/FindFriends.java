package com.example.androidsgv.bikebuddies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * This class displays the list of friends a user has, and allows the user to select which friend
 * to invite on a bike ride. The user is then directed to the SetDateTime activity.
 */

public class FindFriends extends ActionBarActivity {

    private static int FINISH_CODE = 2;
    private static String finishString = "Finish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        // Make logo show up in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_friends, menu);
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

    //Function called when button is selected to invite a friend, per activity's xml file
    public void setDateTime(View v) {
        String friendName;
        Intent intent = new Intent(this,SetDateTime.class);

        //Added information that hardcodes the friend invited for notification purposes
        TextView tv = (TextView) v.findViewById(v.getId());
        friendName = "Name Error";
        if (v.getId() == R.id.friend1Button){
            friendName = getResources().getString(R.string.friend1);
        }
        if (v.getId() == R.id.friend2Button){
            friendName = getResources().getString(R.string.friend2);
        }
        if (v.getId() == R.id.friend3Button){
            friendName = getResources().getString(R.string.friend3);
        }

        Log.d("Sam", "friend name to pass is:" + friendName);
        intent.putExtra("name_of_friend", friendName);
        //End additional information for notification purposes

        //Starts activity to select time and date for invitation with friend
        startActivityForResult(intent, FINISH_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FINISH_CODE && resultCode == FINISH_CODE) {
            finish();
        }
    }
}
