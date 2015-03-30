package com.example.androidsgv.bikebuddies;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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


public class RideScreen extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener,
        View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private PopupWindow endRidePopup;
    private static final String goToLeaderBoardKey = "GoToLeaderBoardKey";
    private static final int RIDE_CODE = 1;
    private Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_screen);
        setUpChronometer();
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        popupInit();

    }

    private void setUpChronometer() {
        mChronometer = (Chronometer) findViewById(R.id.total_time_text);
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
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 13));
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
    // TODO Should update distance figures.
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LatLng newLatlng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatlng));
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

        // Add the appropriate text for the finished ride statistics.
        TextView distanceText = (TextView) topLevelLayout.getChildAt(distanceIndex);
        TextView distanceResult = (TextView) findViewById(R.id.total_distance_text);
        String distanceResultStr = distanceResult.getText().toString();
        distanceText.setText(distanceText.getText() + " " + distanceResultStr);
        endRidePopup.showAtLocation(findViewById(R.id.top_layout), Gravity.CENTER_HORIZONTAL,0,0);


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
        LinearLayout popupLayout = new LinearLayout(this);
        popupLayout.setBackgroundColor(0xFFFFFFFF);
        popupLayout.setOrientation(LinearLayout.VERTICAL);

        TextView finishedMessage = new TextView(this);
        finishedMessage.setText(R.string.finish_message);
        finishedMessage.setTextSize(16);
        popupLayout.addView(finishedMessage);

        TextView totalTime = new TextView(this);
        totalTime.setText(R.string.total_time);
        totalTime.setTextSize(16);
        popupLayout.addView(totalTime);

        TextView totalDistance = new TextView(this);
        totalDistance.setText(R.string.total_distance);
        totalDistance.setTextSize(16);
        popupLayout.addView(totalDistance);

        Button goBackButton = new Button(this);
        goBackButton.setOnClickListener(this);
        goBackButton.setText("Home");
        goBackButton.setTextSize(16);
        popupLayout.addView(goBackButton);

        Button leaderBoardButton = new Button(this);
        leaderBoardButton.setOnClickListener(this);
        leaderBoardButton.setText("Go to Leaderboard");
        leaderBoardButton.setTextSize(16);
        popupLayout.addView(leaderBoardButton);

        endRidePopup = new PopupWindow(popupLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        endRidePopup.setContentView(popupLayout);
    }

    // Listener for button in popup listener
    public void onClick(View v) {
        String text = ((Button) v).getText().toString();
        if (text.equals("Home")) {
            endRidePopup.dismiss();
            finish();
        } else {
            endRidePopup.dismiss();
            Intent intent = new Intent();
            intent.putExtra(goToLeaderBoardKey,true);
            setResult(RIDE_CODE,intent);
            finish();
        }

    }
}
