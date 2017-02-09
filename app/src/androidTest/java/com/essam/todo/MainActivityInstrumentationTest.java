package com.essam.todo;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.essam.todo.ui.views.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by essam on 08/02/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.todo_text)).perform(typeText("Hello")).check(matches(withText("Hello")));
    }

    @Test
    public void validateImageView() {
        onView(withId(R.id.add_todo)).perform(click());
    }

    @Test
    public void validateRecyclerView() {

        onView(withId(R.id.recycler_todo)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }
}
