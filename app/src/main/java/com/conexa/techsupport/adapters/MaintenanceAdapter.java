package com.conexa.techsupport.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.conexa.techsupport.R;
import com.conexa.techsupport.detailTask;
import com.conexa.techsupport.models.Task;
import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.ViewHolder> {

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    private final List<Task> taskList;
    private final OnTaskClickListener listener;

    public MaintenanceAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudul, tvNamaPelanggan, tvKontak, tvTanggal, tvStatus, tvIssue;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvNamaPelanggan = itemView.findViewById(R.id.tvNamaPelanggan);
            tvKontak = itemView.findViewById(R.id.tvTanggal);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvIssue = itemView.findViewById(R.id.tvIssue);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cardView = itemView.findViewById(R.id.cardTask);
        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_maintenance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.tvJudul.setText("Maintenance Jaringan");
        holder.tvNamaPelanggan.setText("Pelanggan: " + task.getNamaPelanggan());
        holder.tvKontak.setText("Kontak : "+task.getKontak());
        holder.tvTanggal.setText("Jadwal : "+task.getTanggal());
        holder.tvIssue.setText("Issue : "+task.getIssue());
        holder.tvStatus.setText("Status: " + task.getStatus());
        holder.cardView.setOnClickListener(v -> listener.onTaskClick(task));
        // Custom warna status
//        switch (task.getStatus()) {
//            case "In Progress":
//                holder.cardView.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.error));
//                break;
//            case "Completed":
//                holder.cardView.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.error));
//                break;
//            default:
//                holder.cardView.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.error));
//        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateData(List<Task> newList) {
        taskList.clear();
        taskList.addAll(newList);
        notifyDataSetChanged();
    }
}
