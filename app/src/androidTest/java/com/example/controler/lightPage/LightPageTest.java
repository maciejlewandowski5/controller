package com.example.controler.lightPage;

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

public class LightPageTest {

    @Rule
    public ActivityTestRule<LightPage> mActivityTestRule = new ActivityTestRule<LightPage>(LightPage.class);

    private LightPage LightPage = null;

    @Before
    public void setUp() throws Exception {
        LightPage = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        LightPage = null;
    }

    @Test
    public void useAppContext() {
        assertNotNull(LightPage);
    }
}