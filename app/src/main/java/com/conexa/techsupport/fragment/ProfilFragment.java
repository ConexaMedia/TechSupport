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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilFragment extends Fragment {
    private TextView tvNamaTeknisi, tvNRK;
    private CardView btnLogout;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstancState){

        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        tvNamaTeknisi = view.findViewById(R.id.nama_teknisi);
        tvNRK = view.findViewById(R.id.NRK);
        btnLogout = view.findViewById(R.id.btn_logout_card);

        loadUserData();

        btnLogout.setOnClickListener(v -> logoutUser());
        return view;
    }

    private void loadUserData(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("teknisi")
                .child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nama = snapshot.child("nama").getValue(String.class);
                    String nrk = snapshot.child("noRegister").getValue(String.class);

                    tvNamaTeknisi.setText(nama);
                    tvNRK.setText(nrk);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });
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
