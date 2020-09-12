package com.example.controler.musicPage;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class MusicPageTest {

    @Rule
    public ActivityTestRule<MusicPage> mActivityTestRule = new ActivityTestRule<MusicPage>(MusicPage.class);

    private MusicPage MusicPage = null;

    @Before
    public void setUp() throws Exception {
        MusicPage = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        MusicPage = null;
    }

    @Test
    public void useAppContext() {
        assertNotNull(MusicPage);
    }
}