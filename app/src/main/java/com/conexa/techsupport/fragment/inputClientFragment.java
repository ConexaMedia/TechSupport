package com.conexa.techsupport.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.conexa.techsupport.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class inputClientFragment extends Fragment {
    private TextInputEditText inputNama, inputKontak, inputAlamat, inputJadwal;
    private AutoCompleteTextView inputPaket;
    private DatabaseReference aktivasiRef;
    private ArrayAdapter<String> paketAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_input_client, container, false);

        aktivasiRef = FirebaseDatabase.getInstance().getReference("tasks/aktivasi");

        inputNama = view.findViewById(R.id.inputNamaClient);
        inputKontak = view.findViewById(R.id.inputKontakClient);
        inputPaket = view.findViewById(R.id.inputPaketInet);
        inputAlamat = view.findViewById(R.id.inputAlamatClient);
        inputJadwal = view.findViewById(R.id.inputJadwal);
        Button btnSubmit = view.findViewById(R.id.btn_submitClient);

        //panggil fungsi dropddown paket
        setupPaketDropdown();
        //panggil datepicker
        inputJadwal.setOnClickListener(v -> showDatePicker());

        //button submit
        btnSubmit.setOnClickListener(v -> submitData());

        return view;
    }

    //fungsi tamppil dropdown paket
    private void setupPaketDropdown(){
        String [] paketList = {
                "10 Mbps",
                "20 Mbps",
                "30 Mbps",
                "50 Mbps"
        };
        paketAdapter = new ArrayAdapter<>(requireContext(), R.layout.item_dropdown_role, paketList);
        inputPaket.setAdapter(paketAdapter);
    }

    //funsgi tampil datetimepicker
    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(requireContext(),
                (view, year, month, day) -> {
                    String date = String.format("%02d-%02d-%04d", day, month+1, year);
                    inputJadwal.setText(date);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    //submit ke database
    private void submitData(){
        if(TextUtils.isEmpty(inputNama.getText())){
            inputNama.setError("Nama Harus Diisi");
            return;
        }

        DatabaseReference aktivasiRef = FirebaseDatabase.getInstance()
                .getReference("tasks/aktivasi");

        aktivasiRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int nextNumber = 1;
                if (snapshot.exists()){
                    for(DataSnapshot task : snapshot.getChildren()){
                        String lastKey = task.getKey();
                        if(lastKey != null && lastKey.startsWith("act")){
                            try{
                                nextNumber = Integer.parseInt(lastKey.replace("act","")) + 1;
                            }catch (NumberFormatException e){
                                Log.e("TaskID", "Error parsing task number", e);
                            }
                        }
                    }
                }

                String taskId = "act" + nextNumber;
                saveTaskData(aktivasiRef.child(taskId));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void saveTaskData(DatabaseReference taskRef){
        Map<String, Object> taskData = new HashMap<>();
        taskData.put("namaPelanggan", inputNama.getText().toString());
        taskData.put("kontak", inputKontak.getText().toString());
        taskData.put("paket", inputPaket.getText().toString());
        taskData.put("alamat", inputAlamat.getText().toString());
        taskData.put("tanggal", convertDateToFirebaseFormat(inputJadwal.getText().toString()));
        taskData.put("status", "Pending");
        taskData.put("createdAt", ServerValue.TIMESTAMP);

        taskRef.setValue(taskData)
                .addOnSuccessListener(aVoid ->{
                    showSuccesDialog();
                    clearForm();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Gagal : "+ e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    private void showSuccesDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Berhasil")
                .setMessage("Data telah masuk ke antrian aktivasi")
                .setPositiveButton("OK", null)
                .show();
    }

    private void clearForm() {
        // Reset semua input field
        inputNama.getText().clear();
        inputKontak.getText().clear();
        inputPaket.setText(""); // Untuk AutoCompleteTextView
        inputAlamat.getText().clear();
        inputJadwal.getText().clear();

        // Reset error
        inputNama.setError(null);
        inputKontak.setError(null);
        inputAlamat.setError(null);
        inputJadwal.setError(null);

        // Fokus ke field pertama
        inputNama.requestFocus();
    }

    private String convertDateToFirebaseFormat(String originalDate) {
        try {
            // Format tanggal dari UI (contoh: "06 Jun 2025" atau "06/06/2025")
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

            // Format yang diinginkan untuk Firebase (YYYY-MM-DD)
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = inputFormat.parse(originalDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e("DateConversion", "Error parsing date: " + originalDate, e);
            return originalDate; // Fallback ke format asli jika parsing gagal
        }
    }
}
