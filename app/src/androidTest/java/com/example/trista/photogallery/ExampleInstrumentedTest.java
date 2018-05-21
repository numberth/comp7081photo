package com.example.trista.photogallery;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.trista.photogallery.myapplication.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public int all, date, loc, cap, mix;

    @Before
    public void initVal(){
        all = 3;
        date = 3;
        loc = 2;
        cap = 1;
        mix = 1;
    }

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testSearchEmpty(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        for (int i = 0; i<= all; i++){
            onView(withId(R.id.clickRight)).perform(click());
        }
    }

    @Test
    public void testSearchLocation(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText("bby"), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        for (int i = 0; i<= loc; i++){
            onView(withId(R.id.clickRight)).perform(click());
        }
    }

    @Test
    public void testSearchDate(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText("20180501"), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText("20180530"), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        for (int i = 0; i<= date; i++){
            onView(withId(R.id.clickRight)).perform(click());
        }
    }

    @Test
    public void testSearchCaption(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_caption)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        for (int i = 0; i<= cap; i++){
            onView(withId(R.id.clickRight)).perform(click());
        }
    }

    @Test
    public void testSearchAll(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText("20180501"), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText("20180530"), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText("bby"), closeSoftKeyboard());
        onView(withId(R.id.search_caption)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        for (int i = 0; i<= mix; i++){
            onView(withId(R.id.clickRight)).perform(click());
        }
        Log.d("Espresso test:", "TEST COMPLETE!");
    }
}
