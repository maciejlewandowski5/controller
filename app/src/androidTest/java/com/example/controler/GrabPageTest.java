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

public class GrabPageTest {

    @Rule
    public ActivityTestRule<GrabPage> mActivityTestRule = new ActivityTestRule<GrabPage>(GrabPage.class);

    private GrabPage GrabPage = null;

    @Before
    public void setUp() throws Exception {
        GrabPage = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        GrabPage = null;
    }

    @Test
    public void useAppContext() {
        assertNotNull(GrabPage);
    }
}