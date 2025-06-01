package com.conexa.techsupport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class detailTask extends Fragment {
    private TextView tvHeader, tvJudul, tvNamaPelanggan, tvAlamat, tvKontak,
            tvTanggal, tvStatus, tvDeskripsi;
    private String kontakPelanggan, alamatPelanggan;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_detail_task, container, false);

        // Inisialisasi View
        tvHeader = view.findViewById(R.id.tvHeader);
        tvNamaPelanggan = view.findViewById(R.id.tvNamaPelanggan);
        tvAlamat = view.findViewById(R.id.tvAlamat);
        tvKontak = view.findViewById(R.id.tvKontak);
        tvTanggal = view.findViewById(R.id.tvTanggal);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvDeskripsi = view.findViewById(R.id.tvDeskripsi);

        // Ambil data dari arguments
        Bundle args = getArguments();
        if (args != null) {
            String taskId = args.getString("task_id");
            String taskType = args.getString("task_type");
            loadTaskData(taskId, taskType);
        }

        return view;
    }

    private void loadTaskData(String taskId, String taskType){
        DatabaseReference taskRef = FirebaseDatabase.getInstance()
                .getReference("tasks")
                .child(taskType)
                .child(taskId);

        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    tvNamaPelanggan.setText("Nama Pelanggan : " + snapshot.child("namaPelanggan").getValue(String.class));
                    tvAlamat.setText("Alamat : "+snapshot.child("alamat").getValue(String.class));
                    tvKontak.setText("Kontak : "+snapshot.child("kontak").getValue(String.class));
                    tvTanggal.setText("Tanggal : "+ snapshot.child("tanggal").getValue(String.class));
                    tvStatus.setText("Status : "+ snapshot.child("status").getValue(String.class));

                    if (taskType.equals("maintenance")) {
                        tvHeader.setText("Detail Maintenance");
                        tvDeskripsi.setText(snapshot.child("issue").getValue(String.class));
                    } else {
                        tvHeader.setText("Detail Aktivasi");
                        tvDeskripsi.setText(snapshot.child("paket").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(),
                        "Gagal memuat data: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
