package com.example.recipefinder;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Mock
    Context context;

    @Rule
    public ActivityTestRule<MainActivity> searchFragmentTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickSearchButton(){
        Espresso.onView(ViewMatchers.withId(R.id.main_button))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.main_constraint))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.result_layout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void clickSettingsButton(){
        Espresso.onView(ViewMatchers.withId(R.id.item_settings))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.main_constraint))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.setting_layout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.recipefinder", appContext.getPackageName());
    }
}
