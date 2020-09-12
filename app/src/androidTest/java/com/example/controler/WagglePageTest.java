package com.example.controler;

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

public class WagglePageTest {

    @Rule
    public ActivityTestRule<WagglePage> mActivityTestRule = new ActivityTestRule<WagglePage>(WagglePage.class);

    private WagglePage WagglePage = null;

    @Before
    public void setUp() throws Exception {
        WagglePage = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        WagglePage = null;
    }

    @Test
    public void useAppContext() {
        assertNotNull(WagglePage);
    }
}