package com.example.controler.musicPage;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentSongValuesTest {

    @Test
    public void song1ValuesTest() {
        CurrentSongValues currentSongValues = new CurrentSongValues(1);

        assertEquals(currentSongValues.animPosition, 0);
        assertEquals(currentSongValues.songPosition, 0);

        for (int i = 0; i < 15; i++) {
            if (i < 4) {
                assertEquals(currentSongValues.songText[i], "On va au bal");
            }
            else if (i < 8) {
                assertEquals(currentSongValues.songText[i], "On va du ciel");
            }
            else {
                assertEquals(currentSongValues.songText[i], "On va au bal");
            }
        }
    }
}