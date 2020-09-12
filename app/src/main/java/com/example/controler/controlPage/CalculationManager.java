package com.example.controler.controlPage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static java.lang.Math.asin;
import static java.lang.Math.tan;

/**
 * Class responsible for calculation position of a cursor.
 */
public class CalculationManager implements SensorEventListener {

    /**
     * Variable responsible for storing multiple data calculating calibration value X for controller.
     */
    private ArrayList<Double> xi = new ArrayList<>();
    /**
     * Variable responsible for storing multiple data calculating calibration value Y for controller.
     */
    private ArrayList<Double> yi = new ArrayList<>();
    /**
     * Variable responsible for storing data for calibrating controller for X axis.
     */
    private Double X;
    /**
     * Variable responsible for storing data for calibrating controller for Y axis.
     */
    private Double Y;
    DatabaseReference xRef;
    DatabaseReference yRef;
    DatabaseReference acceptRef;

    AnimateController animateController;

    /**
     * Variable store information about progress of calibration for controller.
     */
    private int doResetSensorIfThisSmallerThanTen = 0;


    public CalculationManager(AnimateController animateController) {

        this.animateController = animateController;
        // Database and database references.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        xRef = database.getReference("X");
        yRef = database.getReference("Y");
        acceptRef = database.getReference("accept");

        // Calibration
        X = 0.0;
        Y = 0.0;
    }

    /**
     * Empty method.
     *
     * @param sensor sensor to be listen.
     * @param i      accuracy.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Calibrates sensors, and sets X,Y values in databases.
     *
     * @param sensorEvent allow to get values from sensor.
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (doResetSensorIfThisSmallerThanTen > 11) {

            Double angleValue2 = mapRotationToXCursorPosition(sensorEvent.values[2]);
            Double angleValue0 = mapRotationToYCursorPosition(sensorEvent.values[0]);
            if (angleValue2.isInfinite()  || angleValue0.isInfinite()||
                    angleValue2.isNaN() || angleValue0.isNaN() ) {
                xRef.setValue(0);
                yRef.setValue(0);
            }else{
                xRef.setValue(angleValue2);
                yRef.setValue(angleValue0);
                animateController.animate(sensorEvent.values, X, Y);
            }

        } else if (doResetSensorIfThisSmallerThanTen < 11) {

            xi.add((double) sensorEvent.values[2]);
            yi.add((double) sensorEvent.values[0]);
            doResetSensorIfThisSmallerThanTen = doResetSensorIfThisSmallerThanTen + 1;

        } else {

            X = getMean(xi);
            Y = getMean(yi);
            xi.clear();
            yi.clear();
            doResetSensorIfThisSmallerThanTen = doResetSensorIfThisSmallerThanTen + 1;
        }
    }

    /**
     * Formula is mapping by linear interpolation rotation angle to position on a screen.
     * Explanation of formula in technical documentation.
     * @param rotationAroundXAxis Plane value from gameRotationSensor.
     * @return values of x-axis position in px.
     */
    private Double mapRotationToXCursorPosition(float rotationAroundXAxis) {
        return (((1200) * (40 - ( 100 * 0.5 * tan(2 * asin(rotationAroundXAxis - X))))) / (40)) -600;
        // (((1200 - 0) * (70 - (50 + 100 * 0.5 * tan(2 * asin(rotationAroundXAxis - X))))) / (70 - 40)) + 0;
    }

    /**
     * Formula is mapping by linear interpolation rotation angle to position on a screen.
     * Explanation of formula in technical documentation.
     * @param rotationAroundYAxis Plane value from gameRotationSensor.
     * @return Values of y-axis position in px.
     */
    private Double mapRotationToYCursorPosition(float rotationAroundYAxis) {
        return (((630) * (28 - (100 * 0.5 * tan(2 * asin(rotationAroundYAxis - Y))))) / (28)) -315;
        //  (((630 - 0) * (64 - (50 + 100 * 0.5 * tan(2 * asin(rotationAroundYAxis - Y))))) / (64 - 39)) + 0;
    }

    /**
     * Used for calculating calibration.
     *
     * @param a array with values to calculate mean.
     * @return Mean value from a.
     */
    private Double getMean(ArrayList<Double> a) {
        double sum = 0.0;
        for (Double i : a) {
            sum = sum + i;
        }
        return sum / a.size();
    }

    /**
     * Reset screen controller to center and starts calibration process.
     */
    public void resetMousePointer() {
        xRef.setValue(600);
        yRef.setValue(315);
        doResetSensorIfThisSmallerThanTen = 0;
    }
}
