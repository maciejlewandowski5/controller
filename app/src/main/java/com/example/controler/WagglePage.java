package com.example.controler;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import com.example.controler.controlPage.ContrlPage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class responsible for sending information about number of phone shakes.
 */
public class WagglePage extends AppCompatActivity implements SensorEventListener {
    private DatabaseReference triggerReference;
    private SensorManager sensorManager;
    private Sensor linearAccelerationSensor;
    /**
     * Number of phone movements. At initialization set to 0.
     */
    private int triggerCount = 0;
    RealTimeNavigation realTimeNavigation;

    /**
     * Method responsible for initializing variables when WagglePage activity starts.
     *
     * @param savedInstanceState bundle for passing arguments.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waggle_page);

        // Database and database references.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        triggerReference = database.getReference("trigger");


        // Listeners
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        if (sensorManager != null) {
            linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        } else {
            Toast.makeText(this, "No linear acceleration sensor", Toast.LENGTH_SHORT).show();
            finish();
        }
        sensorManager.registerListener(WagglePage.this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_GAME);


        //Navigation
        realTimeNavigation = new RealTimeNavigation();
        realTimeNavigation.active(getBaseContext(), this);


    }

    /**
     * Linear acceleration sensor are registered.
     */
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(WagglePage.this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_GAME);
        triggerCount = 0;
    }


    /**
     * Linear acceleration sensor are unregistered.
     */
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(WagglePage.this);
        triggerReference.setValue(0);
        triggerCount = 0;
    }

    /**
     * Synchronises triggerCount and calculate it.
     * @param event Event to get values.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] >= 10 || event.values[1] >= 10
                || event.values[2] >= 10 || event.values[0] <= -10
                || event.values[1] <= -10 || event.values[2] <= -10) {
            triggerCount++;
            triggerReference.setValue(triggerCount / 50);
        }

    }

    /**
     * Empty method
     * @param sensor sensor
     * @param accuracy accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Starts controller activity.
     * @param view control button activity.
     */
    public void goToControler(View view) {
        Intent myIntent = new Intent(getBaseContext(), ContrlPage.class);
        startActivity(myIntent);
    }

    /**
     * Empty method to stop changing screen when this activity is required to move to another slide.
     */
    @Override
    public void onBackPressed()
    {

    }
}
