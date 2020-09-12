package com.example.controler.waterPage.pDrawings;

import processing.core.PApplet;

public class PDrawing {
    PApplet baseSketch;
    float width;
    float height;

    public PDrawing(PApplet baseSketch,float width,float height) {
        this.baseSketch = baseSketch;
        this.width = width;
        this.height = height;
    }

}
