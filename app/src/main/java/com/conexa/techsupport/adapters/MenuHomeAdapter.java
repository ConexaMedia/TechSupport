package com.conexa.techsupport.adapters;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.conexa.techsupport.R;
import com.conexa.techsupport.models.MenuAdmin;

import java.util.List;

public class MenuHomeAdapter extends RecyclerView.Adapter<MenuHomeAdapter.MenuViewHolder> {
    private List<MenuAdmin> menuItems;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(MenuAdmin item);
    }

    public MenuHomeAdapter(List<MenuAdmin>items, OnItemClickListener listener){
        this.menuItems = items;
        this.listener = listener;
    }

    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_admin, parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHomeAdapter.MenuViewHolder holder, int position) {
        MenuAdmin item = menuItems.get(position);
        holder.ivIcon.setImageResource(item.getIconRes());
        holder.tvTitle.setText(item.getTitle());
        holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.getContext(), item.getBackgroundColorRes())
        );
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }
    public int getItemCount(){
        return menuItems.size();
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        LinearLayout container;
        ImageView ivIcon;
        TextView tvTitle;

        public MenuViewHolder(View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.card_menu);
            container = itemView.findViewById(R.id.container);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
