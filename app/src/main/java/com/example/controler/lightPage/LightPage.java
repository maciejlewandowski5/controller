package com.example.controler.lightPage;

import com.example.controler.R;
import com.example.controler.RealTimeNavigation;
import com.example.controler.controlPage.ContrlPage;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import static android.hardware.Sensor.TYPE_LIGHT;

/**
 * Class responsible for managing actions about light sensor.
 */
public class LightPage extends AppCompatActivity  {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    AnimateLight animateLight;
    LightSensorListener lightEventListener;



    /**
     * Variables are initialized. Listener for light sensor is set.
     * @param savedInstanceState Bundle to pass arguments.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_page);



        // Animation
        animateLight = new AnimateLight(findViewById(R.id.ellipse_light));


        // Navigation
        RealTimeNavigation realTimeNavigation = new RealTimeNavigation();
        realTimeNavigation.active(getBaseContext(),this);



        // Listeners
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        assert sensorManager != null;
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);
        if(lightSensor == null) {
            Toast.makeText(this, "No light sensor detected", Toast.LENGTH_SHORT).show();
            finish();
        }
        lightEventListener = new LightSensorListener(animateLight);
        sensorManager.registerListener(lightEventListener,lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * Registers light sensor listener.
     */
    @Override
    protected void onResume()  {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Unregisters light sensor listener.
     */
    @Override
    protected void onPause()  {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

    /**
     * Starts ControlPage.
     * @param view View of the pressed button.
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

