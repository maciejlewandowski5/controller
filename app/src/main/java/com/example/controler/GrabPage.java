package com.example.controler;

import com.example.controler.controlPage.ContrlPage;

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


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class responsible for sending information about proximity sensor.
 */
public class GrabPage extends AppCompatActivity {
    private DatabaseReference proximityRef;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;

    /**
     * Method responsible for initializing variables when GrabPage activity starts.
     * @param savedInstanceState bundle for passing arguments.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_page);


        // Database and references.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        proximityRef = database.getReference("proximity");

        // Navigation.
        RealTimeNavigation realTimeNavigation = new RealTimeNavigation();
        realTimeNavigation.active(getBaseContext(), this);


        // Listeners
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        assert sensorManager != null;
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor == null) {
            Toast.makeText(this, "No proximity sensor detected", Toast.LENGTH_SHORT).show();
            finish();
        }
        proximityEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.values[0] != 0)
                    proximityRef.setValue(0);
                else
                    proximityRef.setValue(1);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    /**
     * Listener is register on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(proximityEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    /**
     * Listener is unregister in pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximityEventListener);
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
