package com.example.controler.musicPage;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class TextColorsTest {

    @Test
    public void getBackground() {
        TextColors textColors = new TextColors(10,20);

        int background = textColors.getBackground();

        assertEquals(20, background);
    }

    @Test
    public void getForeground() {
        TextColors textColors = new TextColors(10,20);

        int foreground = textColors.getForeground();

        assertEquals(10, foreground);
    }

    @Test
    public void setSlideColors() {
        TextColors textColors = new TextColors(10,20);
        int[] expectedColors = new int [100];

        //1
        textColors.setSlideColors(0);
        Arrays.fill(expectedColors, textColors.getBackground());

        assertArrayEquals(expectedColors, textColors.getColors());

        //2
        textColors.setSlideColors(40);
        int[] realShaders = textColors.getShaders();

        Arrays.fill(expectedColors, 0, 30, 10);
        for (int i = 30; i < 40; i++) {
            expectedColors[i] = realShaders[i-30];
        }
        Arrays.fill(expectedColors, 40, 99, 20);

        assertArrayEquals(expectedColors, textColors.getColors());

        //3
        textColors.setSlideColors(110);
        Arrays.fill(expectedColors, 10);

        assertArrayEquals(expectedColors, textColors.getColors());
    }

    @Test
    public void setFadeColors() {
        TextColors textColors = new TextColors(10,20);
        int[] expectedColors = new int [100];

        //1
        textColors.setFadeColors(0);
        Arrays.fill(expectedColors, 20);

        assertArrayEquals(expectedColors, textColors.getColors());

        //2
        textColors.setFadeColors(99);
        Arrays.fill(expectedColors, 10);

        assertArrayEquals(expectedColors, textColors.getColors());

        //3
        textColors.setFadeColors(60);
        Arrays.fill(expectedColors, -1145423264);

        assertArrayEquals(expectedColors, textColors.getColors());
    }
}