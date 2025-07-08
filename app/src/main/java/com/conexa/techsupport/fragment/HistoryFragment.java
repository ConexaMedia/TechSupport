package com.conexa.techsupport.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conexa.techsupport.HistoryPdfExport;
import com.conexa.techsupport.R;
import com.conexa.techsupport.adapters.HistoryAdapter;
import com.conexa.techsupport.models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private boolean isOwner = false ;
    private Button btnExport;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recylerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new HistoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        btnExport = view.findViewById(R.id.btn_export);
        btnExport.setOnClickListener(v -> exportToPdf());

        checkUserRole();

        loadHistoryData();
    return view;
    }

    private void loadHistoryData(){
        DatabaseReference historyRef = FirebaseDatabase.getInstance()
                .getReference("history");

        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task> historyList = new ArrayList<>();
                for (DataSnapshot typeSnapshot : snapshot.getChildren()){
                    for (DataSnapshot taskSnapshot : typeSnapshot.getChildren()){
                        Task task = taskSnapshot.getValue(Task.class);
                        task.setId(taskSnapshot.getKey());
                        task.setJenis(typeSnapshot.getKey());
                        historyList.add(task);
                    }
                }

                adapter.updateData(historyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Gagal load history", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void checkUserRole (){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference("teknisi")
                .orderByChild("uid").equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot data : snapshot.getChildren()){
                                String role = data.child("role").getValue(String.class);
                                if ("Owner".equals(role)){
                                    isOwner = true;
                                    btnExport.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Gagal Memeriksa Role", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void exportToPdf(){
        DatabaseReference historyRef = FirebaseDatabase.getInstance()
                .getReference("history");
        historyRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> historyData = (Map<String, Object>) snapshot.getValue();
                if(historyData != null){
                    Map<String, Object> aktivasiData = (Map<String, Object>) historyData.get("aktivasi");
                    Map<String, Object> maintenanceData = (Map<String, Object>) historyData.get("maintenance");

                    new HistoryPdfExport(requireContext()).exportFullReport(aktivasiData, maintenanceData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Gagal load history", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
