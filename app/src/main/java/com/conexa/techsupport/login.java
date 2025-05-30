package com.conexa.techsupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btn_login;
    private Button btn_registrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        btn_login = findViewById(R.id.btn_login);
        btn_registrasi = findViewById(R.id.btn_registrasi);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(validasi(user,pass)){
                    Intent intent = new Intent(login.this, MainActivity.class);
                    intent.putExtra(panelHome.NamaTeknisi, user);
                    intent.putExtra(panelProfile.NamaTeknisi, user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(login.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegist = new Intent(login.this, RegistrasiTeknisi.class);
                startActivity(toRegist);
            }
        });
    }
    private boolean validasi(String user, String password){
        return user.equals("PABPROJECT")&& password.equals("123456");
    }

}
