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

public class AktivasiAdapter extends RecyclerView.Adapter<AktivasiAdapter.ViewHolder> {
    public interface OnTaskClickListener{
        void onTaskClick(Task task);
    }
    private final OnTaskClickListener listener;
    private final List<Task> taskList;

    public AktivasiAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aktivasi, parent, false);
        return new ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvJudul, tvNamaPelanggan, tvKontak, tvPaket, tvTanggal, tvStatus;
        public CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvNamaPelanggan = itemView.findViewById(R.id.tvNamaPelanggan);
            tvKontak = itemView.findViewById(R.id.tvKontak);
            tvPaket = itemView.findViewById(R.id.tvPaket);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cardView = itemView.findViewById(R.id.cardTask);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.tvJudul.setText("Aktivasi Internet Baru");
        holder.tvNamaPelanggan.setText("Nama : "+task.getNamaPelanggan());
        holder.tvKontak.setText("Kontak : "+task.getKontak());
        holder.tvPaket.setText("Paket : "+task.getPaket());
        holder.tvTanggal.setText("Jadwal : "+task.getTanggal());
        holder.tvStatus.setText("Status : "+task.getStatus());
        holder.cardView.setOnClickListener(v -> listener.onTaskClick(task));

        //custom warna berdasarkan status
//        if (task.getStatus().equals("Pending")){
//            holder.cardView.setCardBackgroundColor(holder.itemView.getContext().getResources()
//                    .getColor(R.color.error));
//        } else {
//            holder.cardView.setCardBackgroundColor(holder.itemView.getContext().getResources()
//                    .getColor(R.color.floating));
//        }
    }

    public int getItemCount(){
        return taskList.size();
    }

    public void updateData(List<Task> newList){
        taskList.clear();
        taskList.addAll(newList);
        notifyDataSetChanged();

    }
}
