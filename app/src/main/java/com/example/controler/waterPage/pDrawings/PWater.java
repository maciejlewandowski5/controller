package com.example.controler.waterPage.pDrawings;

import processing.core.PApplet;

import static java.lang.StrictMath.sin;

/**
 * Class responsible for drawing water on Processing sketch.
 */
public class PWater extends PDrawing {


    public PWater(PApplet baseSketch,float width,float height) {
        super(baseSketch,width,height);
    }

    /**
     * Animated water flow from glass on the left side (watching on the screen of the phone). Animation based on frames.
     * @param frameCount Specified number of frame for which animation should be drown.
     */
    public void drawFlowingWaterLeft(int frameCount) {
        baseSketch.fill(0, 0, 255);
        baseSketch.stroke(0, 0, 255);
        baseSketch.pushMatrix();
        baseSketch.beginShape();
        baseSketch.vertex(width / 12f * 3, height / 4f);
        baseSketch.bezierVertex(width / 3f, height / 5f, 0, height / 4f, -50, (float) (height / 4f - 50 + 25 * sin(frameCount * 0.05f)));
        baseSketch.bezierVertex(-50, height / 4f - 50, (float) (width / 12f * 2 + 25 * sin(frameCount * 0.05f)), height / 3f, width / 12f * 3, height / 4f);
        baseSketch.endShape();
        baseSketch.popMatrix();
    }

    /**
     * Animated water flow from glass on the right side (watching on the screen of the phone). Animation based on frames.
     * @param frameCount Specified number of frame for which animation should be drown.
     */
    public void drawFlowingWaterRight(int frameCount) {
        baseSketch.fill(0, 0, 255);
        baseSketch.stroke(0, 0, 255);
        baseSketch.beginShape();
        baseSketch.vertex(width / 12f * 9f, height / 4f);
        baseSketch.bezierVertex(width / 3f * 2, height / 5f, width, height / 4f, width + 50, (float) (height / 4f - 50 + 25 * sin(frameCount * 0.05f)));
        baseSketch.bezierVertex(width + 50, height / 4f - 50, (float) (width / 12f * 11 + 25 * sin(frameCount * 0.05f)), height / 3f, width / 12f * 9f, height / 4f);
        baseSketch.endShape();
    }

    /**
     * Draw blue rectangle responsible for imitating water. Rotates rectangle with phone.
     * @param alpha Angle of water rotation.
     */
    public void drawWater(double alpha) {
        baseSketch.fill(50, 50, 240);
        baseSketch.pushMatrix();
        baseSketch.translate(width / 2f, height / 2f); // Set xy axis origin in middle of the screen
        baseSketch.rotate((float)alpha);
        baseSketch.rect(-width, 0, 2 * width, height);
        baseSketch.popMatrix();
    }
}
