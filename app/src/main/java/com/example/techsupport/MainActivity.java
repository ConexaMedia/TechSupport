package com.example.techsupport;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationBarView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_bottom);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_panel, panelHome.newInstance("Tech Support")).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment setFragment = null;

                if(item.getItemId() == R.id.nav_home){
                    setFragment = new panelHome();
                } else if (item.getItemId() == R.id.nav_task) {
                    setFragment = new panelTask();
                } else if (item.getItemId() == R.id.nav_history) {
                    setFragment = new panel_history();
                } else if (item.getItemId() == R.id.nav_profile) {
                    setFragment = new panelProfile();
                }

                if (setFragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.content_panel, setFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });


    }
}