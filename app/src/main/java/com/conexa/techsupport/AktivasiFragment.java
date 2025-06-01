package com.conexa.techsupport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.conexa.techsupport.adapters.AktivasiAdapter;
import com.conexa.techsupport.models.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AktivasiFragment extends Fragment {

    private RecyclerView recyclerView;
    private AktivasiAdapter adapter;
    private DatabaseReference aktivasiRef;

    public AktivasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_aktivasi, container, false);

        recyclerView = view.findViewById(R.id.recylerViewAktivasi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AktivasiAdapter(new ArrayList<>(), task ->{
            detailTask fragment = new detailTask();
            Bundle args = new Bundle();
            args.putString("task_id", task.getId());
            args.putString("task_type", "aktivasi");
            fragment.setArguments(args);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_panel, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(adapter);

        //load data di database
        aktivasiRef = FirebaseDatabase.getInstance().getReference("tasks/aktivasi");
        loadAktivasiData();

        return view;
    }

    private void loadAktivasiData(){
        aktivasiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task> aktivasiList = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()) {
                    Task task = data.getValue(Task.class);
                    if (task != null) {
                        task.setId(data.getKey());
                        task.setJenis("aktivasi");
                        aktivasiList.add(task);
                    }
                }
                adapter.updateData(aktivasiList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}