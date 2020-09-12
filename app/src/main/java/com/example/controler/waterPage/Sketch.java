// Sketch.java
package com.example.controler.waterPage;
import processing.core.PApplet;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.controler.waterPage.pDrawings.PGUI;
import com.example.controler.waterPage.pDrawings.PWater;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Class responsible for graphical content of WagglePage. Created with use Processing for Android.
 */
public class Sketch extends PApplet {

    /**
     * Angle of phone rotation around axis perpendicular to screen.(Pointing your eye.)
     */
    @SuppressWarnings("FieldCanBeLocal") // Method draw starts 30 times a second. There is no need to reserve every time a space for alpha variable.
    private double alpha;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference splashRef = database.getReference("splash");
    private DatabaseReference changeRef = database.getReference("change");

    private PGUI pGUI;
    private PWater pWater;
    private WaterPageGameRotationSensorListener listener;


    /**
     * Describes setting to run application.
     */
    public void settings() {
        size(width, height);
    }

    /**
     * Setup game rotation sensor listener.
     */
    public void setup() {
        Context context = getActivity();
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        assert manager != null;
        Sensor gameRotationSensor = manager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        listener = new WaterPageGameRotationSensorListener();
        manager.registerListener(listener, gameRotationSensor, SensorManager.SENSOR_DELAY_FASTEST);


        pGUI = new PGUI(this,width,height);
        pWater = new PWater(this,width,height);

    }

    /**
     * Draw content on display.
     */
    public void draw() {
        background(220);
        stroke(255);
        strokeWeight(0);

        alpha = listener.getAlpha();

        pWater.drawWater(alpha);
        pGUI.drawGlass();

        if (alpha < -0.940) { // 0.940 Is angle when water touches corner of the glass aprox.53 d.
            pWater.drawFlowingWaterRight(frameCount);
            splashRef.setValue(1);
        } else if (alpha > 0.940) {
            pWater.drawFlowingWaterLeft(frameCount);
            splashRef.setValue(1);
        }
        pGUI.drawGUI();
    }

    /**
     * Sets change reference to 1, in order to navigate to controlPage.
     */
    @Override
    public void mousePressed() {
        super.mousePressed();
        if (mouseX >= (width / 12 * 10 - width / 10 * 3 / 2) && mouseX <= width / 12 * 10 + width / 10 * 3 / 2 && mouseY >= height / 100 * 85 - width / 10 * 3 / 2 && mouseY <= height / 100 * 85 + width / 10 * 3 / 2) {
            changeRef.setValue(1);
        }
    }

}