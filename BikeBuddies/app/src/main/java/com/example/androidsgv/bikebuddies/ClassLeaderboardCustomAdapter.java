package com.example.androidsgv.bikebuddies;

/**
 * This class defines the custom Adapter for the Class Leaderboard that displays
 * students and their statistics.
 *
 * Created by Sam Echevarria on 3/30/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassLeaderboardCustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] personName;
    private final String[] timeAndMileage;

    public ClassLeaderboardCustomAdapter(Activity context, String[] personName, String[] timeAndMileage) {
        super(context, R.layout.leaderboard_list, personName);

        this.context=context;
        this.personName=personName;
        this.timeAndMileage =timeAndMileage;
    }

    //Defines what a view looks like in a row of the Adapter, setting the necessary information
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.leaderboard_list, null,true);

        TextView txtName = (TextView) rowView.findViewById(R.id.name_of_other_user);
        TextView txtData = (TextView) rowView.findViewById(R.id.other_user_time_dist);

        txtName.setText(personName[position]);

        txtData.setText(timeAndMileage[position]);
        return rowView;

    };
}