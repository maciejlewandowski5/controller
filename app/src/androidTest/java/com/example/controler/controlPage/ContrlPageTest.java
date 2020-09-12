package com.example.controler.controlPage;

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

public class ContrlPageTest {

    @Rule
    public ActivityTestRule<ContrlPage> mActivityTestRule = new ActivityTestRule<ContrlPage>(ContrlPage.class);

    private ContrlPage contrlPage = null;

    @Before
    public void setUp() throws Exception {
        contrlPage = mActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        contrlPage = null;
    }

    @Test
    public void useAppContext() {
        assertNotNull(contrlPage);
    }
}