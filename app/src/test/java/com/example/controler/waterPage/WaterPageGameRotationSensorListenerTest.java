package com.example.controler.waterPage;

import org.junit.Test;

import static org.junit.Assert.*;

public class WaterPageGameRotationSensorListenerTest {

    @Test
    public void getAlpha() {
        WaterPageGameRotationSensorListener waterPageGameRotationSensorListener = new WaterPageGameRotationSensorListener();
        assertEquals(waterPageGameRotationSensorListener.getAlpha(), 0d, 0);
    }
}