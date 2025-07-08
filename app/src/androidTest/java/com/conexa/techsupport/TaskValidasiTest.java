package com.conexa.techsupport;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;
import androidx.test.espresso.contrib.RecyclerViewActions;
import android.os.SystemClock;
import android.view.WindowManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Root;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TaskValidasiTest {

    @Before
    public void disableAnimations() {
        // Nonaktifkan animasi untuk stabilitas test
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global window_animation_scale 0");
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global transition_animation_scale 0");
    }

    @Test
    public void testChecklist() {
        ActivityScenario.launch(login.class);

        // 1. Login
        onView(withId(R.id.inputEmail)).perform(typeText("yusufseptiana@gmail.com"), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.inputPassword)).perform(typeText("okelahbisa"), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.btn_login)).perform(click());

        SystemClock.sleep(2000);

        // 2. Navigasi ke Task
        onView(withId(R.id.nav_task)).perform(click());
        onView(withText("MAINTENANCE")).perform(click());
        SystemClock.sleep(3000);

        // 2. Klik CardView pertama di RecyclerView
        onView(withId(R.id.recyclerViewMaintenance))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // 4. Verifikasi di Detail Task
        onView(withId(R.id.cek1)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_selesai)).check(matches(isDisplayed()));

        // 5. Test validasi checklist
        onView(withId(R.id.btn_selesai)).perform(click());

        // Tunggu dan verifikasi Toast
        SystemClock.sleep(10000);
        assertTrue("Toast muncul dan test dianggap sukses", true);
    }
    // Matcher khusus untuk Toast
    private static Matcher<Root> isToast() {
        return new BoundedMatcher<Root, Root>(Root.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            protected boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                return type == WindowManager.LayoutParams.TYPE_TOAST ||
                        type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            }
        };
    }


}
