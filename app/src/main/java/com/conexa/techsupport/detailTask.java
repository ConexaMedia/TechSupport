package com.conexa.techsupport;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class detailTask extends Fragment {
    private TextView tvHeader, tvJudul, tvNamaPelanggan, tvAlamat, tvKontak,
            tvTanggal, tvStatus, tvDeskripsi, list1, list2, list3, list4, list5;
    private String kontakPelanggan, alamatPelanggan;
    private String taskId, taskType;
    private CheckBox cekList1, cekList2, cekList3, cekList4, cekList5;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        taskId = getArguments().getString("task_id");
        taskType = getArguments().getString("task_type");

        View view = inflater.inflate(R.layout.fragment_detail_task, container, false);

        // Inisialisasi View
        tvHeader = view.findViewById(R.id.tvHeader);
        tvNamaPelanggan = view.findViewById(R.id.tvNamaPelanggan);
        tvAlamat = view.findViewById(R.id.tvAlamat);
        tvKontak = view.findViewById(R.id.tvKontak);
        tvTanggal = view.findViewById(R.id.tvTanggal);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvDeskripsi = view.findViewById(R.id.tvDeskripsi);

        //ListPekerjaan dan checkbox
        list1 = view.findViewById(R.id.list1);
        list2 = view.findViewById(R.id.list2);
        list3 = view.findViewById(R.id.list3);
        list4 = view.findViewById(R.id.list4);
        list5 = view.findViewById(R.id.list5);

        cekList1 = view.findViewById(R.id.cek1);
        cekList2 = view.findViewById(R.id.cek2);
        cekList3 = view.findViewById(R.id.cek3);
        cekList4 = view.findViewById(R.id.cek4);
        cekList5 = view.findViewById(R.id.cek5);

        MaterialButton btnSelesai = view.findViewById(R.id.btn_selesai);
        btnSelesai.setOnClickListener(v -> {
            if (validateChecklist()) {
                pindahkanKeHistory();
            }
        });

        // Ambil data dari arguments
        Bundle args = getArguments();
        if (args != null) {
            String taskId = args.getString("task_id");
            String taskType = args.getString("task_type");
            loadTaskData(taskId, taskType);
            loadChecklist(taskType);
        }
        return view;
    }

    //Ambil data client berdasarkan task yang dipilih
    private void loadTaskData(String taskId, String taskType) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance()
                .getReference("tasks")
                .child(taskType)
                .child(taskId);

        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tvNamaPelanggan.setText("Nama Pelanggan : " + snapshot.child("namaPelanggan").getValue(String.class));
                    tvAlamat.setText("Alamat : " + snapshot.child("alamat").getValue(String.class));
                    tvKontak.setText("Kontak : " + snapshot.child("kontak").getValue(String.class));
                    tvTanggal.setText("Tanggal : " + snapshot.child("tanggal").getValue(String.class));
                    tvStatus.setText("Status : " + snapshot.child("status").getValue(String.class));

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
    //Ambil Data Pekerjaan yang sesuai
    private void loadChecklist(String taskType) {
        DatabaseReference defaultChecklistRef = FirebaseDatabase.getInstance()
                .getReference("checklist")
                .child(taskType);

        defaultChecklistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list1.setText(snapshot.child("list1").getValue(String.class));
                    list2.setText(snapshot.child("list2").getValue(String.class));
                    list3.setText(snapshot.child("list3").getValue(String.class));
                    list4.setText(snapshot.child("list4").getValue(String.class));
                    list5.setText(snapshot.child("list5").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Gagal load checklist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //proses selesaikan pekerjaan
    private void pindahkanKeHistory() {
        // 1. Validasi null
        if (taskType == null || taskId == null) {
            Log.e("FirebaseError", "taskType atau taskId null!");
            Toast.makeText(getContext(), "Data task tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        //Debug log
        Log.d("FirebasePath", "Memindahkan: " + taskType + "/" + taskId);

        DatabaseReference taskRef = FirebaseDatabase.getInstance()
                .getReference("tasks")
                .child(taskType)
                .child(taskId);
        DatabaseReference historyRef = FirebaseDatabase.getInstance()
                .getReference("history")
                .child(taskType)
                .child(taskId);

        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Map<String, Object> historyData = new HashMap<>();
                    historyData.putAll((Map<String, Object>) snapshot.getValue());
                    historyData.put("status", "Selesai");
                    historyData.put("completedAt", ServerValue.TIMESTAMP);
                    historyData.put("teknisi", "CNT06213");

                    historyRef.setValue(historyData)
                            .addOnSuccessListener(aVoid -> {
                                taskRef.removeValue()
                                        .addOnSuccessListener(aVoid1 -> {
                                            Toast.makeText(getContext(), "Task selesai!", Toast.LENGTH_SHORT).show();
                                            requireActivity().onBackPressed();
                                        });
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // Validasi checklist
    private boolean validateChecklist() {
        if (!cekList1.isChecked() || !cekList2.isChecked() || !cekList3.isChecked()) {
            Toast.makeText(getContext(), "Lengkapi checklist terlebih dahulu!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
