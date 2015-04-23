package com.example.androidsgv.bikebuddies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class FindFriends extends ActionBarActivity {

    private static int FINISH_CODE = 2;
    private static String finishString = "Finish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
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

    public void setDateTime(View v) {
        String friendName;
        Intent intent = new Intent(this,SetDateTime.class);
        //ADDED FOR NOTIFICATION PURPOSES
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
        //END ADDITIONS
        startActivityForResult(intent, FINISH_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FINISH_CODE && resultCode == FINISH_CODE) {
            finish();
        }
    }
}
