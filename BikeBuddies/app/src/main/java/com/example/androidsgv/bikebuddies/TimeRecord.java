package com.example.androidsgv.bikebuddies;

import android.util.Log;

/**
 * This is a class meant to ease up some of the onerous work done in
 * manipulating time data in other classes. It allows easy creation of a
 * TimeRecord from a string, changing a record back to a string, and adding
 * records together.
 *
 * Created by James Bowen on 4/30/15.
 */
public class TimeRecord {

    private int hours;
    private int minutes;
    private int seconds;

    public TimeRecord(int h, int m, int s)
    {
        hours = h;
        minutes = m;
        seconds = s;
    }

    public TimeRecord(String recordString) {
        String[] components = recordString.split(":");
        if (components.length == 2) {
            minutes = Integer.parseInt(components[0]);
            seconds = Integer.parseInt(components[1]);
            hours = 0;
        } else if (components.length == 3) {
            hours = Integer.parseInt(components[0]);
            minutes = Integer.parseInt(components[1]);
            seconds = Integer.parseInt(components[2]);
        } else {
            Log.e("James", "Wrong number of colons in time String!");
            Log.e("James",recordString);
        }
    }

    public void add(TimeRecord other)
    {
        int totalSeconds = seconds + other.getSeconds();
        seconds = totalSeconds % 60;

        int minutesCarryOver = totalSeconds / 60;
        int totalMinutes = minutes + other.getMinutes() + minutesCarryOver;
        minutes = totalMinutes % 60;

        int hoursCarryOver = totalMinutes / 60;
        int totalHours = hours + other.getHours() + hoursCarryOver;
        hours = totalHours;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (hours < 10) {
            buffer.append('0');
        }
        buffer.append(hours);
        buffer.append(':');

        if (minutes < 10) {
            buffer.append('0');
        }
        buffer.append(minutes);
        buffer.append(':');

        if (seconds < 10) {
            buffer.append('0');
        }
        buffer.append(seconds);

        return buffer.toString();
    }


}
