package com.conexa.techsupport;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class panelHome extends Fragment {

    private TextView tvNamaTeknisi, tvNoRegist;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ConstraintLayout layoutOlt;

    public panelHome(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("teknisi");
        }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout fragment\
        View view = inflater.inflate(R.layout.fragment_panel_home, container, false);

        tvNamaTeknisi = view.findViewById(R.id.show_nama_teknisi);
        tvNoRegist = view.findViewById(R.id.show_noregist);
        layoutOlt = view.findViewById(R.id.layout_olt);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userUID = currentUser.getUid();

            mDatabase.orderByChild("uid").equalTo(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot techSnapshot : snapshot.getChildren()){
                            String nama = techSnapshot.child("nama").getValue(String.class);
                            String noRegister = techSnapshot.child("noRegister").getValue(String.class);

                            tvNamaTeknisi.setText(nama);
                            tvNoRegist.setText(noRegister);

                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        layoutOlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), panelOLT.class);
                startActivity(intent);
            }
        });
        return view;
    }

}

