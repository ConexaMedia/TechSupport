package com.conexa.techsupport;

import android.os.Bundle;
import android.view.MenuItem;
import com.conexa.techsupport.fragment.panelHomeAdmin;
import com.conexa.techsupport.fragment.ProfilFragment;
import com.conexa.techsupport.fragment.HistoryFragment;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityAdmin extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth fAuth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_admin);

        fAuth = FirebaseAuth.getInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_panel_admin,new panelHomeAdmin()).commit();

        bottomNavigationView = findViewById(R.id.nav_bottom_admin);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment setFragment = null;

                if (item.getItemId() == R.id.nav_home){
                    setFragment = new panelHomeAdmin();
                } else if (item.getItemId() == R.id.nav_data) {
                    setFragment = new HistoryFragment();
                } else if (item.getItemId() == R.id.nav_profile) {
                    setFragment = new ProfilFragment();
                }
                if (setFragment !=null ){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.content_panel_admin,setFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}
