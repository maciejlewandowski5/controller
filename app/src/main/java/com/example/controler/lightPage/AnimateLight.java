package com.example.controler.lightPage;

import android.view.View;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

/**
 * Class responsible for animating lightPage activity.
 */
public class AnimateLight {

    SpringAnimation ellipseLightX;
    SpringAnimation ellipseLightY;


    /**
     *
     * @param ellipse View of ellipse that will be animated based on light sensor values.
     */
    public AnimateLight(View ellipse){
        ellipseLightX = new SpringAnimation(ellipse, DynamicAnimation.SCALE_X,0);
        ellipseLightY = new SpringAnimation(ellipse,DynamicAnimation.SCALE_Y,0);

        ellipseLightX.setStartValue(0);
        ellipseLightX.setMinValue(0);
        ellipseLightX.setMaxValue(10000000000L);
        ellipseLightX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        ellipseLightX.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);

        ellipseLightY.setStartValue(0);
        ellipseLightY.setMinValue(0);
        ellipseLightY.setMaxValue(10000000000L);
        ellipseLightY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        ellipseLightY.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);

    }


    /**
     * Animates scale x and y of an ellipse.
     * @param lightSensorValue Value from light sensor, ath specified time.
     */
    public void animate(float lightSensorValue){
        if(lightSensorValue <=300){ // Final scale 3/2 gives full size of the animated ellipse
            ellipseLightX.animateToFinalPosition(lightSensorValue/200); // .../200 <- coefficient to lower the size of ellipse after animation.
            ellipseLightY.animateToFinalPosition(lightSensorValue/200);}
        else{
            ellipseLightX.cancel();
            ellipseLightY.cancel();
        }

    }

}
