package com.example.androidsgv.bikebuddies;

/**
 * Created by Sam on 4/26/2015.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

/**
 * most of code from http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen
 */
public class SplashActivity extends ActionBarActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences("NotifyBadge",0);
        SharedPreferences.Editor editor = preferences.edit();

        int numNotify = 0;

        editor.putInt("numNotify",numNotify);
        editor.commit();

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainScreen.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
