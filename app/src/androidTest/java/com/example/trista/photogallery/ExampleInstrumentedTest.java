package com.example.trista.photogallery;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import com.example.trista.photogallery.myapplication.MainActivity;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public int all, date, loc, cap, mix;

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with "+childPosition+" child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };
    }

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
        onView(withId(R.id.imagegallery)).perform(ViewActions.swipeUp());
    }

    @Test
    public void testSearchLocation(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText("bby"), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        onView(withId(R.id.imagegallery)).perform(ViewActions.swipeUp());
    }

    @Test
    public void testSearchDate(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText("20180601"), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText("20180630"), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        onView(withId(R.id.imagegallery)).perform(ViewActions.swipeUp());
    }

    @Test
    public void testSearchCaption(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_caption)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        onView(withId(R.id.imagegallery)).perform(ViewActions.swipeUp());
    }

    @Test
    public void testSearchAll(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_startdate)).perform(typeText("20180601"), closeSoftKeyboard());
        onView(withId(R.id.search_enddate)).perform(typeText("20180630"), closeSoftKeyboard());
        onView(withId(R.id.search_location)).perform(typeText("bby"), closeSoftKeyboard());
        onView(withId(R.id.search_caption)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.search_searchbtn)).perform(click());
        onView(withId(R.id.imagegallery)).perform(ViewActions.swipeUp());
    }

    @Test
    public void testDeselect() {
        onView(withId(R.id.imagegallery))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.imagegallery))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.imagegallery))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.imagegallery))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(nthChildOf(withId(R.id.imagegallery), 0))
                .check(matches(hasDescendant(withText(""))));
        onView(nthChildOf(withId(R.id.imagegallery), 2))
                .check(matches(hasDescendant(withText(""))));
    }

    @Test
    public void testSelect(){
        onView(withId(R.id.imagegallery))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(nthChildOf(withId(R.id.imagegallery), 0))
                .check(matches(hasDescendant(withText("Selected"))));
        onView(withId(R.id.imagegallery))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(nthChildOf(withId(R.id.imagegallery), 2))
                .check(matches(hasDescendant(withText("Selected"))));
    }
}
