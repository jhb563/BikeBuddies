package com.example.androidsgv.bikebuddies;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;


public class LeaderboardScreen extends ActionBarActivity {

    ListView list;
    String[] people;
    String[] data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_screen);
        // Make logo show up in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        SharedPreferences preferences = getSharedPreferences("Class",0);

        /******************************
         *  THE 3 LINES BELOW CLEAR OUT THE SHARED PREFERENCES FILE. UNCOMMENT THIS CODE
         *  ONCE IF YOU WANT TO REMOVE ANY PREVIOUS RIDE ENTRIES.
         ******************************/
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.commit();


         /*
          * Use the following lines (until editor.commit()) once to re-instantiate hard-coded
          * data.
          */
//        String[] peopleOld = getResources().getStringArray(R.array.peoplearray);
//        String[] dataOld = getResources().getStringArray(R.array.timemileagearray);
//
//        for (int j = 0; j < peopleOld.length; ++j) {
//            String timeDistance = dataOld[j];
//            editor.putString(peopleOld[j],peopleOld[j]+"   "+timeDistance);
//        }
//
//        editor.commit();

        Map<String,String> peopleEntries = (Map<String,String>) preferences.getAll();

        String[][] allEntries = new String[peopleEntries.size()][3];

        int i = 0;
        for (Map.Entry<String,String> person : peopleEntries.entrySet()) {
            String[] components = person.getValue().split("   ");
            allEntries[i] = components;
            ++i;
        }
        int numEntries = i;

        Arrays.sort(allEntries, new Comparator<String[]>() {
            @Override
            public int compare(String[] lhs, String[] rhs) {
                int length1 = lhs[2].length();
                Double distanceLHS = Double.valueOf(lhs[2].substring(0,length1-2));
                int length2 = rhs[2].length();
                Double distanceRHS = Double.valueOf(rhs[2].substring(0,length2-2));
                return (int) (distanceRHS - distanceLHS);
            }
        });


        people = new String[numEntries];
        data = new String[numEntries];

        for (i = 0; i < numEntries; ++i) {
            people[i] = allEntries[i][0];
            String distance = allEntries[i][2].substring(0,allEntries[i][2].length()-2);
            double distanceDoub = Double.valueOf(distance);
            String correctDistance = String.format("%.2f",distanceDoub);
            data[i] = allEntries[i][1] + "   " + correctDistance;
        }


//        people = getResources().getStringArray(R.array.peoplearray);
//        data = getResources().getStringArray(R.array.timemileagearray);

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
