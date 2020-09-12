package com.example.controler.waterPage;

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

public class WaterPageTest {

    @Rule
    public ActivityTestRule<WaterPage> mActivityTestRule = new ActivityTestRule<WaterPage>(WaterPage.class);

    private WaterPage WaterPage = null;

    @Before
    public void setUp() throws Exception {
        WaterPage = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        WaterPage = null;
    }

    @Test
    public void useAppContext() {
        assertNotNull(WaterPage);
    }
}