package com.example.controler.controlPage;

import android.view.View;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import static java.lang.Math.asin;

/**
 * Class responsible for animation in controlPage.
 */
public class AnimateController {

    /**
     * Table with all spring animations that will be used.
     */
    private SpringAnimation[] springAnimations = new SpringAnimation[7];


    /**
     * Initializes all springAnimations.Animations: springAnimation[0] - TRANSLATION_X, springAnimation[1] - TRANSLATION_Y,
     *  springAnimation[2] - ROTATION,  springAnimation[3] - ROTATION_X, springAnimation[4] - ROTATION_Y, springAnimation[5] - SCALE_X
     *   springAnimation[6] - SCALE_Y,
     *  @param virtualJ View of joystick to animate.
     * @param button View of area around accept button.
     */
    public AnimateController(View virtualJ, View button) {

        springAnimations[0] = new SpringAnimation(virtualJ, DynamicAnimation.TRANSLATION_X, 200);
        springAnimations[1] = new SpringAnimation(virtualJ, DynamicAnimation.TRANSLATION_Y, 200);
        springAnimations[2] = new SpringAnimation(virtualJ, DynamicAnimation.ROTATION, 0);
        springAnimations[3] = new SpringAnimation(virtualJ, DynamicAnimation.ROTATION_X, 0);
        springAnimations[4] = new SpringAnimation(virtualJ, DynamicAnimation.ROTATION_Y, 0);
        springAnimations[5] = new SpringAnimation(button, DynamicAnimation.SCALE_X, 0);
        springAnimations[6] = new SpringAnimation(button, DynamicAnimation.SCALE_Y, 0);

        for (SpringAnimation springAnimation : springAnimations) {
            springAnimation.setStartValue(0);
            springAnimation.setMinValue(-500);
            springAnimation.setMaxValue((float) 500); // in radians
            springAnimation.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            springAnimation.getSpring().setStiffness(SpringForce.STIFFNESS_HIGH);
        }

    }

    /**
     * Method animate all spring values.
     * @param sensorEventValues Table with values form game rotation sensor.
     * @param xCalibration Calibration for xAxis.
     * @param yCalibration Calibration for yAxis.
     */
    public void animate(float[] sensorEventValues, double xCalibration, double yCalibration) {

        @SuppressWarnings("WrapperTypeMayBePrimitive") // Can not because of methods isInfinite isNaN
        Double angleValue2= Math.toDegrees(2 * asin(sensorEventValues[2] - xCalibration));
        @SuppressWarnings("WrapperTypeMayBePrimitive") // Can not because of methods isInfinite isNaN
        Double angleValue0=Math.toDegrees(2 * asin(sensorEventValues[0] - yCalibration));
        if (!angleValue2.isInfinite() && !angleValue2.isNaN() && !angleValue0.isInfinite() && !angleValue0.isNaN()) {
            // 2*asin(sensorEventValues[...] - ...Calibration) <- convert game rotation sensor to angle.
            // ...2 or ...0.75 <- scale coefficients
            springAnimations[0].animateToFinalPosition(-angleValue2.floatValue() * 2);
            springAnimations[1].animateToFinalPosition(-angleValue0.floatValue() * 2);
            springAnimations[2].animateToFinalPosition(-angleValue2.floatValue() * 0.75f);
            springAnimations[3].animateToFinalPosition(-angleValue0.floatValue() * 0.75f);

            if (sensorEventValues[0] - yCalibration < 0) {
                // ... ) + 1 <- starting size coefficient.
                springAnimations[5].animateToFinalPosition((float) (2 * asin(sensorEventValues[0] - yCalibration) * 0.25) + 1);
            } else {
                springAnimations[5].animateToFinalPosition(-(float) (2 * asin(sensorEventValues[0] - yCalibration) * 0.25) + 1);
            }

            if (sensorEventValues[2] - xCalibration < 0) {
                springAnimations[6].animateToFinalPosition(+(float) (2 * asin(sensorEventValues[2] - xCalibration) * 0.25) + 1);

            } else {
                springAnimations[6].animateToFinalPosition(-(float) (2 * asin(sensorEventValues[2] - xCalibration) * 0.25) + 1);
            }

        }
    }

}
