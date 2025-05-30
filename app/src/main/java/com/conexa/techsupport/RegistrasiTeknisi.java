package com.conexa.techsupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrasiTeknisi extends AppCompatActivity {

    private Button btn_backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrasi_teknisi);

        btn_backLogin = findViewById(R.id.btn_backLogin);

        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backLogin = new Intent(RegistrasiTeknisi.this, login.class);
                startActivity(backLogin);

            }
        });

    }
}