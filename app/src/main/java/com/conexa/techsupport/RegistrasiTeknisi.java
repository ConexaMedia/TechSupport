package com.conexa.techsupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrasiTeknisi extends AppCompatActivity {

    private TextInputEditText inputNoRegistKaryawan, inputNamaTeknisi, inputEmail, inputPassword;
    private Button btn_registrasi, btn_backLogin;

    private FirebaseAuth Auth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrasi_teknisi);

        Auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("teknisi");

        //baca inputan xml
        inputNoRegistKaryawan = findViewById(R.id.inputNoRegistKaryawan);
        inputNamaTeknisi = findViewById(R.id.inputNamaTeknisi);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btn_registrasi = findViewById(R.id.btn_registrasi);
        btn_backLogin = findViewById(R.id.btn_backLogin);

        btn_registrasi.setOnClickListener(v -> registerUser());
        btn_backLogin.setOnClickListener(v ->  {
                Intent backLogin = new Intent(RegistrasiTeknisi.this, login.class);
                startActivity(backLogin);
                finish();
        });

    }

    //ambil dari inputan
    private void registerUser(){
        String noRegist = inputNoRegistKaryawan.getText().toString().trim();
        String namaTeknisi = inputNamaTeknisi.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString();

        //validasi inputan
        if (namaTeknisi.isEmpty() || noRegist.isEmpty() || email.isEmpty() || password.isEmpty()){
            if (namaTeknisi.isEmpty()) inputNamaTeknisi.setError("Nama Tidak Boleh Kosong");
            if (noRegist.isEmpty()) inputNoRegistKaryawan.setError("Harap Isi Nomor Karyawan");
            if (email.isEmpty()) inputEmail.setError("Email Tidak Boleh Kosong");
            if (password.length()< 6)inputPassword.setError("Password Minimal 6 Karakter");
        }

        //simpan data di Realtime Database
        Auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        //user berhasil dibuat dan disimpan ke database
                        FirebaseUser firebaseUser = Auth.getCurrentUser();
                        if(firebaseUser != null){

                            String uid = firebaseUser.getUid();
                            User teknisi = new User(noRegist, namaTeknisi, email, firebaseUser.getUid());

                            databaseReference.child(noRegist).setValue(teknisi)
                                    .addOnCompleteListener(saveTask ->{
                                        if (saveTask.isSuccessful()){
                                            Toast.makeText(RegistrasiTeknisi.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(RegistrasiTeknisi.this, login.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(RegistrasiTeknisi.this, "Gagal Simpan Data : " +task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(RegistrasiTeknisi.this, "Registrasi Gagal : "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
    }

    public static class User{
        public String noRegister;
        public String nama;
        public String email;
        public String uid;

        public User(){

        }

        public User(String noRegister, String nama, String email, String uid){
            this.noRegister = noRegister;
            this.nama = nama;
            this.email = email;
            this.uid = uid;
        }
    }
}