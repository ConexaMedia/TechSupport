package com.conexa.techsupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private Button btn_registrasi;
    private DatabaseReference databaseReference;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btn_login = findViewById(R.id.btn_login);
        btn_registrasi = findViewById(R.id.btn_registrasi);

        Auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("teknisi");

        btn_login.setOnClickListener(v -> loginUser());
        btn_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegist = new Intent(login.this, RegistrasiTeknisi.class);
                startActivity(toRegist);
                finish();
            }
        });
    }

    private void loginUser() {
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(login.this, "Silakan isi username dan password", Toast.LENGTH_SHORT).show();
            return;
        }

        Auth.signInWithEmailAndPassword(user,pass)
                .addOnCompleteListener(task ->{
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = Auth.getCurrentUser();
                        if(firebaseUser !=null ){
                            String uid = firebaseUser.getUid();

                            //cari data teknisi berdasarkan email
                            databaseReference.orderByChild("uid").equalTo(uid)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {

                                                Intent intent = new Intent(login.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(login.this, "Teknisi belum terdaftar", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}