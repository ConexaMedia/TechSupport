package com.conexa.techsupport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conexa.techsupport.R;
import com.conexa.techsupport.adapters.MenuHomeAdapter;
import com.conexa.techsupport.detailTask;
import com.conexa.techsupport.models.MenuAdmin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class panelHomeAdmin extends Fragment {
    private TextView tvNamaTeknisi, tvNoRegist;
    private FirebaseAuth fAuth;
    private DatabaseReference fDatabase;
    private RecyclerView recyclerView;
    private List<MenuAdmin> menuItems;
    private MenuHomeAdapter menuAdapter;

    public panelHomeAdmin(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance().getReference("teknisi");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_admin, container, false);

        tvNamaTeknisi = view.findViewById(R.id.show_nama_teknisi);
        tvNoRegist = view.findViewById(R.id.show_noregist);
        recyclerView = view.findViewById(R.id.recyclerMenuAdmin);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        //ambil data user untuk cardview
        FirebaseUser currentUser = fAuth.getCurrentUser();
        if (currentUser != null){
            String userUID = currentUser.getUid();

            fDatabase.orderByChild("uid").equalTo(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
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
        initMenuItems();

        MenuHomeAdapter adapter = new MenuHomeAdapter(menuItems, item ->{
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

            switch (item.getTitle()){
                case "Input Client Baru":
                    fragmentManager.beginTransaction()
                                   .replace(R.id.content_panel_admin, new inputClientFragment())
                                    .addToBackStack(null)
                                    .commit();
                    break;
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initMenuItems(){
        menuItems = new ArrayList<>();
        menuItems.add(new MenuAdmin("Input Client Baru", R.drawable.poweron, R.color.light_green));
        menuItems.add(new MenuAdmin("Upgrade Speed Client", R.drawable.remote_access, R.color.light_yellow));
        menuItems.add(new MenuAdmin("Laporan", R.drawable.report, R.color.light_blue));
        menuItems.add(new MenuAdmin("Lokasi Client", R.drawable.location_2, R.color.light_red));
    }
}
