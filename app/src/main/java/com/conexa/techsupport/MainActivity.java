package com.conexa.techsupport;

import android.os.Bundle;
import android.view.MenuItem;

import com.conexa.techsupport.fragment.HistoryFragment;
import com.conexa.techsupport.fragment.ProfilFragment;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationBarView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_panel, new panelHome()).commit();

        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment setFragment = null;

                if(item.getItemId() == R.id.nav_home){
                    setFragment = new panelHome();
                } else if (item.getItemId() == R.id.nav_task) {
                    setFragment = new panelTask();
                } else if (item.getItemId() == R.id.nav_history) {
                    setFragment = new HistoryFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    setFragment = new ProfilFragment();
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