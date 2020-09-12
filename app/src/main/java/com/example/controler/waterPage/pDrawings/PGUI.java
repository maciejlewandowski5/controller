package com.example.controler.waterPage.pDrawings;

import processing.core.PApplet;

public class PGUI extends PDrawing {


    public PGUI(PApplet baseSketch,float width,float height) {
        super(baseSketch,width,height);
    }

    /**
     * Manges drawing GUI.
     */
    public void drawGUI() {
        drawTopGUI();
        drawBottomGUI();
        drawContextButton();
    }

    /**
     * Draw setup button.
     */
    private void drawContextButton() {
        baseSketch.strokeWeight(0);
        baseSketch.stroke(199, 0, 57);
        baseSketch.fill(199, 0, 57);
        baseSketch.ellipse(width / 12f * 10, height / 100f * 85, width / 10f * 3, width / 10f * 3);
        baseSketch.fill(255);
        baseSketch.stroke(255);
        baseSketch.ellipse(width / 12f * 10, height / 100f * 85, width / 18f, width / 18f);

        baseSketch.strokeWeight(10);
        baseSketch.fill(255, 255, 255, 0);
        baseSketch.stroke(255);
        baseSketch.ellipse(width / 12f * 10, height / 100f * 85, width / 12f * 2, width / 12f * 2);

        baseSketch.stroke(255);
        baseSketch.line(width / 12f * 10, height / 100f * 85 - width / 22f * 2, width / 12f * 10, height / 100f * 85 - width / 22f * 2 - 10);
        baseSketch.line(width / 12f * 10, height / 100f * 85 + width / 22f * 2, width / 12f * 10, height / 100f * 85 + width / 22f * 2 + 10);
        baseSketch.line(width / 12f * 10 - width / 22f * 2, height / 100f * 85, width / 12f * 10 - width / 22f * 2 - 10, height / 100f * 85);
        baseSketch.line(width / 12f * 10 + width / 22f * 2, height / 100f * 85, width / 12f * 10 + width / 22f * 2 + 10, height / 100f * 85);

    }

    /**
     * Draws shapes and sets colors for lower part of GUI.
     */
    private void drawBottomGUI() {
        baseSketch.fill(84, 25, 70);
        baseSketch.pushMatrix();
        baseSketch.rotate(PApplet.PI);
        baseSketch.translate(-width, -height);
        baseSketch.beginShape();
        baseSketch.vertex(-50, height / 5f);
        baseSketch.bezierVertex(width / 5f + 150, height / 8f - 50, width * 5 / 5f, height / 8f, width + 50, height / 10f);
        baseSketch.bezierVertex(width + 50, height / 10f, width + 50, -50, width + 50, -50);
        baseSketch.bezierVertex(width + 50, -50, -50, -50, -50, -50);
        baseSketch.endShape();
        baseSketch.popMatrix();
    }

    /**
     * Draws shapes and sets colors for upper part of GUI. Draws text for app name.
     */
    private void drawTopGUI() {
        baseSketch.fill(84, 25, 70);
        baseSketch.beginShape();
        baseSketch.vertex(-50, height / 5f);
        baseSketch.bezierVertex(width / 5f + 150, height / 8f - 50, width * 5 / 5f, height / 8f, width + 50, height / 10f);
        baseSketch.bezierVertex(width + 50, height / 10f, width + 50, -50, width + 50, -50);
        baseSketch.bezierVertex(width + 50, -50, -50, -50, -50, -50);
        baseSketch.endShape();
        baseSketch.fill(255);
        baseSketch.textAlign(PApplet.CENTER);
        baseSketch.textSize(100);
        baseSketch.text("Contrôleur", width / 2f, height / 12f);

    }

    /**
     * Draw glass as negative space.(Surrounding of a glass are white and visible, glass is empty.)
     */
    public void drawGlass() {
        baseSketch.strokeWeight(0);
        baseSketch.fill(255);
        baseSketch.pushMatrix();
        baseSketch.rect(0, 0, width, height / 4f);
        baseSketch.rect(0, height / 4f * 3, width, height);
        baseSketch.rotate((float) -0.1);
        baseSketch.translate(-width / 5f, 0);
        baseSketch.rect(0, 0, width / 3f, height);
        baseSketch.translate(width / 5f, 0);
        baseSketch.rotate((float) 0.2);
        baseSketch.translate(width / 5f, 0);
        baseSketch.rect(width / 3f * 2, 0, width, height);
        baseSketch.translate(-width / 5f, 0);
        baseSketch.rotate((float) -0.1);
        baseSketch.popMatrix();
    }

}


