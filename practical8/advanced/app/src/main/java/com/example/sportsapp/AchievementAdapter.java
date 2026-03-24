package com.example.sportsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    public interface OnDeleteListener { void onDelete(Achievement a); }

    private List<Achievement> items = new ArrayList<>();
    private final OnDeleteListener listener;

    public AchievementAdapter(OnDeleteListener listener) { this.listener = listener; }

    public void setItems(List<Achievement> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Achievement a = items.get(position);
        holder.textTitle.setText(a.getTitle());
        holder.textSport.setText("Вид спорта: " + a.getSportType());
        holder.textDate.setText("Дата: " + a.getTrainingDate() + (a.getDescription().isEmpty() ? "" : " — " + a.getDescription()));
        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(a));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textSport, textDate;
        Button buttonDelete;
        ViewHolder(View view) {
            super(view);
            textTitle = view.findViewById(R.id.textTitle);
            textSport = view.findViewById(R.id.textSport);
            textDate = view.findViewById(R.id.textDate);
            buttonDelete = view.findViewById(R.id.buttonDelete);
        }
    }
}
