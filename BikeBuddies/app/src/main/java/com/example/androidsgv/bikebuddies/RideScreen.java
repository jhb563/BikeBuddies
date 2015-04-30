package com.example.androidsgv.bikebuddies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;
import com.google.maps.android.SphericalUtil;

import java.util.Calendar;


public class RideScreen extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener,
        View.OnClickListener, Chronometer.OnChronometerTickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private PopupWindow endRidePopup;
    private PopupWindow cancelRidePopup;
    private static final String goToLeaderBoardKey = "GoToLeaderBoardKey";
    private static final int RIDE_CODE = 1;
    private Chronometer mChronometer;
    private long mCurrentTime;
    private double totalDistance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_screen);
        initChronometer();
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        popupInit();
        cancelInit();
        totalDistance = 0;


    }

    private void initChronometer() {
        mChronometer = (Chronometer) findViewById(R.id.total_time_text);
        mChronometer.setOnChronometerTickListener(this);
        mCurrentTime = mChronometer.getBase();
    }

    private void setUpChronometer() {
        mChronometer = (Chronometer) findViewById(R.id.total_time_text);
        mChronometer.setOnChronometerTickListener(this);
        mChronometer.setBase(mCurrentTime);
    }

    // Creates a Google API Client, which we use to interact with Google Play Services
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    // Callback for when we have connected to the client
    public void onConnected(Bundle ConnectedHint) {
        createLocationRequest();
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        startLocationUpdates();

        // Load the map once we have connected
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

    }

    // Needed to implement onCallback interface...I'm not sure what we'll do with this
    public void onConnectionSuspended(int code) {

    }

    // Needed to implement onCallback interface...I'm not sure what we'll do with this
    // We should probably give some kind of an error message...maybe
    public void onConnectionFailed(ConnectionResult res) {

    }

    // Currently gets the current location, moves the map over it, and puts a pointer there.
    public void onMapReady(GoogleMap map) {

        mMap = map;

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        LatLng current;

        if (mLastLocation != null) {
            current = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        } else {
            current = new LatLng(-33.867, 151.206);
        }

//        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 17));
        map.addMarker(new MarkerOptions().position(current));
        mChronometer.start();

    }

    // Sets our location request so that we can get periodic location updates.
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    // Registers our app to get location updates.
    protected void startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
//                requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    // Listener for when the location changes.
    public void onLocationChanged(Location location) {

        double metersToMile = 0.000621371;

        LatLng previousLatlng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());

        mLastLocation = location;
        LatLng newLatlng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatlng));

        double distance = SphericalUtil.computeDistanceBetween(previousLatlng,newLatlng);
        double miles = distance * metersToMile;
        totalDistance += miles;
        TextView distanceText = (TextView) findViewById(R.id.total_distance_text);
        distanceText.setText(String.format("%1.2f",totalDistance));

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(newLatlng));
    }

    // Called when the end ride button is pressed. Brings up a popup which shows the user
    // statistics.
    public void endRide(View v) {
        // Stop the timer
        mChronometer.stop();

        int timeIndex = 1;
        int distanceIndex = 2;

        // Add the appropriate text for the finished ride statistics.
        LinearLayout topLevelLayout = (LinearLayout) endRidePopup.getContentView();
        TextView timeText = (TextView) topLevelLayout.getChildAt(timeIndex);
        Chronometer timeResult = (Chronometer) findViewById(R.id.total_time_text);
        String timeResultStr = timeResult.getText().toString();
        timeText.setText(timeText.getText() + " " + timeResultStr);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        year = year % 100;
        int month = c.get(Calendar.MONTH);
        month += 1;
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);




        // Add the appropriate text for the finished ride statistics.
        TextView distanceText = (TextView) topLevelLayout.getChildAt(distanceIndex);
        TextView distanceResult = (TextView) findViewById(R.id.total_distance_text);
        String distanceResultStr = distanceResult.getText().toString();
        distanceText.setText(distanceText.getText() + " " + distanceResultStr);
        endRidePopup.showAtLocation(findViewById(R.id.top_layout), Gravity.CENTER_HORIZONTAL,0,0);

        String dateTimeString = c.toString();
        String abbrDate = month + "/" + day + "/" + year;

        SharedPreferences preferences = getSharedPreferences("Ride History",0);
        SharedPreferences.Editor editor = preferences.edit();

        String finalString = abbrDate + "," + distanceResultStr + "," + timeResultStr+","+dateTimeString;

        editor.putString(dateTimeString,finalString);
        editor.commit();

        preferences = getSharedPreferences("Class",0);
        String currentUser = "Hannah";
        editor = preferences.edit();
        String[] oldTimeAndDist = preferences.getString(currentUser,"").split("   ");
        if (oldTimeAndDist.length < 3) {
            Log.e("James","Something went wrong");
        } else {
            String oldTime = oldTimeAndDist[1];
//            String oldTimeString = oldTimeAndDist[1];
//            TimeRecord oldTime = new TimeRecord(oldTimeString);
            String[] times = oldTime.split(":");
            int[] intTimes = new int[3];
            for (int i = 0; i < 3; ++i) {
                intTimes[i] = Integer.parseInt(times[i]);
            }

            String[] timesForThisRide = timeResultStr.split(":");
            int[] intRideTimes = new int[timesForThisRide.length];
            for (int i = 0; i < intRideTimes.length; ++i) {
                intRideTimes[i] = Integer.parseInt(timesForThisRide[i]);
            }

            String finalTime = "";
            if (intRideTimes.length == 2) {
                int newSeconds = intRideTimes[1] + intTimes[2];
                int minuteCarry = newSeconds / 60;
                newSeconds = newSeconds % 60;

                int newMinutes = intRideTimes[0] + intTimes[1] + minuteCarry;
                int hourCarry = newMinutes / 60;
                newMinutes = newMinutes % 60;

                int finalHour = intTimes[0] + hourCarry;

                finalTime = finalHour + ":" + newMinutes + ":" + newSeconds;
            } else if (intRideTimes.length == 3) {
                int newSeconds = intRideTimes[2] + intTimes[2];
                int minuteCarry = newSeconds / 60;
                newSeconds = newSeconds % 60;

                int newMinutes = intRideTimes[1] + intTimes[1] + minuteCarry;
                int hourCarry = newMinutes / 60;
                newMinutes = newMinutes % 60;

                int finalHour = intTimes[0] + hourCarry + intRideTimes[0];

                finalTime = finalHour + ":" + newMinutes + ":" + newSeconds;
            } else {
                Log.e("James","Something went wrong with the time!");
            }


            String oldDist = oldTimeAndDist[2];
            Double oldDistanceDoub = Double.valueOf(oldDist.substring(0,oldDist.length() - 2));
            Double newDistance = Double.valueOf(distanceResultStr);
            String newDistanceString = (oldDistanceDoub + newDistance) + "";

            editor.putString(currentUser,currentUser + "   " + finalTime + "   " + newDistanceString+"mi");
            editor.commit();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ride_screen, menu);
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

    // Set up the popup for ending the ride
    private void popupInit() {
        int fontSize = 16;

        LinearLayout popupLayout = new LinearLayout(this);
        popupLayout.setBackgroundColor(0xFFFFFFFF);
        popupLayout.setOrientation(LinearLayout.VERTICAL);

        TextView finishedMessage = new TextView(this);
        finishedMessage.setText(R.string.finish_message);
        finishedMessage.setTextSize(fontSize);
        popupLayout.addView(finishedMessage);

        TextView totalTime = new TextView(this);
        totalTime.setText(R.string.total_time);
        totalTime.setTextSize(fontSize);
        popupLayout.addView(totalTime);

        TextView totalDistance = new TextView(this);
        totalDistance.setText(R.string.total_distance);
        totalDistance.setTextSize(fontSize);
        popupLayout.addView(totalDistance);

        Button goBackButton = new Button(this);
        goBackButton.setOnClickListener(this);
        goBackButton.setText("Home");
        goBackButton.setTextSize(fontSize);
        popupLayout.addView(goBackButton);

        Button leaderBoardButton = new Button(this);
        leaderBoardButton.setOnClickListener(this);
        leaderBoardButton.setText("Go to Leaderboard");
        leaderBoardButton.setTextSize(fontSize);
        popupLayout.addView(leaderBoardButton);

        endRidePopup = new PopupWindow(popupLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        endRidePopup.setContentView(popupLayout);
    }

    private void cancelInit() {
        int fontSize = 16;

        LinearLayout popupLayout = new LinearLayout(this);
        popupLayout.setBackgroundColor(0xFFFFFFFF);
        popupLayout.setOrientation(LinearLayout.VERTICAL);

        TextView cancelMessage = new TextView(this);
        cancelMessage.setText(R.string.cancel_message);
        cancelMessage.setTextSize(fontSize);
        popupLayout.addView(cancelMessage);

        Button yesButton = new Button(this);
        yesButton.setOnClickListener(this);
        yesButton.setText("Yes");
        yesButton.setTextSize(fontSize);
        popupLayout.addView(yesButton);

        Button noButton = new Button(this);
        noButton.setOnClickListener(this);
        noButton.setText("No");
        noButton.setTextSize(fontSize);
        popupLayout.addView(noButton);



        cancelRidePopup = new PopupWindow(popupLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cancelRidePopup.setContentView(popupLayout);
    }


    // Listener for button in popup listener
    public void onClick(View v) {
        String text = ((Button) v).getText().toString();
        if (text.equals("Home")) {
            endRidePopup.dismiss();
            finish();
        } else if (text.equals("No")) {
           cancelRidePopup.dismiss();
        } else if (text.equals("Yes")) {
            cancelRidePopup.dismiss();
            finish();
        } else {
            endRidePopup.dismiss();
            Intent intent = new Intent();
            intent.putExtra(goToLeaderBoardKey,true);
            setResult(RIDE_CODE,intent);
            finish();
        }

    }

    public void onBackPressed() {
        cancelRidePopup.showAtLocation(findViewById(R.id.top_layout), Gravity.CENTER_HORIZONTAL,0,0);
    }

    public void onChronometerTick(Chronometer chronometer) {
        mCurrentTime = chronometer.getBase();
    }


}
