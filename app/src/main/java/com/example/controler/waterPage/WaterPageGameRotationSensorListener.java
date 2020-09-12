package com.example.controler.waterPage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.ArrayList;

import static java.lang.StrictMath.asin;

/**
 * Class responsible for Listener implementation.
 */
public class WaterPageGameRotationSensorListener implements SensorEventListener {
    /**
     * Variable store information about progress of calibration for water rotation angle.
     */
    private int doResetSensorIfThisSmallerThanTen = 0;
    /**
     * Variable responsible for storing multiple data calculating calibration value X for water rotation angle
     * .
     */
    private ArrayList<Double> xi = new ArrayList<>();
    /**
     * Variable responsible for storing data for calibrating controller for X axis rotation.
     */
    private Double X;
    /**
     * Angle of rotation around axis perpendicular to phone screen.(Towards your eye.)
     */
    private Double alpha;


    public WaterPageGameRotationSensorListener(){
    alpha = 0d;
    }

    /**
     * Calculate calibration values or calculate angle of water rotation.
     *
     * @param sensorEvent Event that can provide values for calculation.
     */

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (doResetSensorIfThisSmallerThanTen > 11) {
            if (alpha.isNaN() || alpha.isInfinite()){
                alpha = 2*asin(X);
            }
            else {
                alpha = (2 * asin((float) (sensorEvent.values[2] ))-2*asin(X));//gets calibrated value
            }

        } else if (doResetSensorIfThisSmallerThanTen < 11) {
            xi.add((double) sensorEvent.values[2]);
            doResetSensorIfThisSmallerThanTen = doResetSensorIfThisSmallerThanTen + 1;
        } else {
            X = getMean(xi);
            xi.clear();
            doResetSensorIfThisSmallerThanTen = doResetSensorIfThisSmallerThanTen + 1;
        }


    }

    /**
     * @param a Array  to calculate mean.
     * @return Mean value of specified array.
     */
    private Double getMean(ArrayList<Double> a) {
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum = sum + a.get(i);
        }
        return sum / a.size();
    }

    /**
     * Empty method do nothing.
     *
     * @param sensor   sensor
     * @param accuracy accuracy
     */
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * Value alpha is updated every time game rotation sensor on axis perpendicular to phone changes value.
     *
     * @return Value of water rotation.
     */
    public double getAlpha() {
        return alpha;
    }
}