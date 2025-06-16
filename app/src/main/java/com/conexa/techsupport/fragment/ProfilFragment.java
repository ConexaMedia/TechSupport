package com.conexa.techsupport.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.conexa.techsupport.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.conexa.techsupport.login;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilFragment extends Fragment {
    private TextView tvNamaTeknisi, tvNRK;
    private CardView btnLogout;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancState){

        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        tvNamaTeknisi = view.findViewById(R.id.show_user);
        tvNRK = view.findViewById(R.id.NRK);
        btnLogout = view.findViewById(R.id.btn_logout_card);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("teknisi");

        loadUserData();
        btnLogout.setOnClickListener(v -> logoutUser());
        return view;
    }

    private void loadUserData(){
        FirebaseUser  currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            String userUID = currentUser.getUid();

            mDatabase.orderByChild("uid").equalTo(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot snapshotTech :snapshot.getChildren()){
                            String nama = snapshotTech.child("nama").getValue(String.class);
                            String noRegister = snapshotTech.child("noRegister").getValue(String.class);

                            tvNamaTeknisi.setText(nama);
                            tvNRK.setText(noRegister);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void logoutUser(){
        new AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Anda yakin ingin logout?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
