package com.example.controler.lightPage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class responsible for listening to light sensor values.
 */
public class LightSensorListener implements SensorEventListener {

    AnimateLight animateLight;
    private DatabaseReference lightRef;

    /**
     * Initializes variables.
     * @param animateLight Animation of light ellipse.
     */
    public LightSensorListener(AnimateLight animateLight){
        this.animateLight = animateLight;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        lightRef = database.getReference("light");

    }

    /**
     *  Sets value in database, by linear approximation specified by formula, (255 - 0) * ((event.values[0] - 0) - 0)) / (1000 - 0)) + 0).
     *  where 255 is max value in database for light object (corresponds to alpha chanel in RGBA color system, higher value are replaced with 255).
     *  ... -0 is menial value for RGBA alpha chanel.
     *  event.values[0] - 0 are current and minimal values from light sensor.
     *  (1000 - 0) are maximal and menial values from light sensor.
     *  ... + 0 is shift on results. Here it is 0.
     *
     * WARNING! Formula may produce higher values than 255 because domain of values form light sensor can be higher than 1000.
     * In this case formula start extrapolation rather than interpolation.
     * @param event Event to provide values of light sensor.
     *
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //       lightRef.setValue((((255 - 0) * ((event.values[0] - 0) - 0)) / (1000 - 0)) + 0);
        lightRef.setValue((((255) * ((event.values[0] - 0) - 0)) / (1000)) + 0);
        animateLight.animate(event.values[0]);
    }

    /**
     * Empty method.
     * @param sensor s
     * @param accuracy a
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
