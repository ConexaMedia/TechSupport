package com.conexa.techsupport;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import android.os.SystemClock;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Before
    public void disableAnimations() {
        // Nonaktifkan animasi untuk stabilitas test
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global window_animation_scale 0");
        InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global transition_animation_scale 0");
    }

    @Test // test untuk inputan salah
    public void testTechnicianLogin(){
        // jalankan activity
        ActivityScenario.launch(login.class);

        // Input akun yang salah
        onView(withId(R.id.inputEmail)).perform(typeText("yusufseptiana@gmail.com"));
        onView(withId(R.id.inputPassword)).perform(typeText("123456"));
        onView(withId(R.id.btn_login)).perform(click());

        SystemClock.sleep(3500);

        // Verifikasi tetap di LoginActivity
        onView(withId(R.id.btn_login))  // cek Tombol login masih ada
                .check(matches(isDisplayed()));
    }

}


