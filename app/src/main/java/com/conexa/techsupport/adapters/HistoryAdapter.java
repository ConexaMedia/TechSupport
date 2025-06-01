package com.conexa.techsupport.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.conexa.techsupport.R;
import com.conexa.techsupport.models.Task;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Task> historyList;

    public HistoryAdapter(List<Task> historyList) {
        this.historyList = historyList;
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
            cardView = itemView.findViewById(R.id.cardHistory);
        }
    }

    public void updateData(List<Task> newList){
        historyList.clear();
        historyList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = historyList.get(position);

        holder.tvJudul.setText(task.getId());
        holder.tvNamaPelanggan.setText("Nama : "+task.getNamaPelanggan());
        holder.tvKontak.setText("Kontak : "+task.getKontak());
        holder.tvPaket.setText("Paket : "+task.getPaket());
        holder.tvTanggal.setText("Selesai : "+task.getTanggal());
        holder.tvStatus.setText("Status : "+task.getStatus());

    }
    public int getItemCount(){
        return historyList.size();
    }


}
