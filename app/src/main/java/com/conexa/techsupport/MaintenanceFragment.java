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

import com.conexa.techsupport.adapters.MaintenanceAdapter;
import com.conexa.techsupport.models.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MaintenanceFragment extends Fragment {
    private RecyclerView recylerView;
    private MaintenanceAdapter adapter;
    private DatabaseReference maintenanceRef;

    public MaintenanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);

        recylerView = view.findViewById(R.id.recyclerViewMaintenance);
        recylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MaintenanceAdapter(new ArrayList<>(), task ->{
            detailTask fragment = new detailTask();
            Bundle args = new Bundle();
            args.putString("task_id", task.getId());
            args.putString("task_type", "maintenance");
            fragment.setArguments(args);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_panel,fragment)
                    .addToBackStack(null)
                    .commit();
        });
        recylerView.setAdapter(adapter);

        maintenanceRef = FirebaseDatabase.getInstance().getReference("tasks/maintenance");
        loadMaintenanceData();

        return view;
    }

    private void loadMaintenanceData(){
        maintenanceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task> maintenanceList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Task task = data.getValue(Task.class);
                    if (task != null) {
                        task.setId(data.getKey());
                        maintenanceList.add(task);
                    }
                }
                adapter.updateData(maintenanceList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}