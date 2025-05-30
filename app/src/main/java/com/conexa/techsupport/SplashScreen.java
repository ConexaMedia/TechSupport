package com.conexa.techsupport;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() ->{
            Intent intent = new Intent(SplashScreen.this, login.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}