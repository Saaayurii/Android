package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    public interface OnItemActionListener {
        void onEdit(int position);
        void onDelete(int position);
    }

    private final List<Purchase> items;
    private final OnItemActionListener listener;

    public PurchaseAdapter(List<Purchase> items, OnItemActionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Purchase p = items.get(position);
        holder.textName.setText(p.getName());
        holder.textDetails.setText("Цена: " + p.getPrice() + " руб., Кол-во: " + p.getQuantity());
        holder.buttonEdit.setOnClickListener(v -> listener.onEdit(position));
        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(position));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textDetails;
        Button buttonEdit, buttonDelete;
        ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDetails = itemView.findViewById(R.id.textDetails);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
