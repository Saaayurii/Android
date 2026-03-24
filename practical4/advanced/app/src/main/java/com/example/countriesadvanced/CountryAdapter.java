package com.example.countriesadvanced;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    public interface OnCountryClickListener {
        void onCountryClick(Country country);
    }

    private List<Country> countries;
    private OnCountryClickListener listener;

    public CountryAdapter(List<Country> countries, OnCountryClickListener listener) {
        this.countries = countries;
        this.listener = listener;
    }

    public void updateList(List<Country> newList) {
        this.countries = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.tvCountryName.setText(country.getName());
        holder.tvCapital.setText(country.getCapital());
        holder.itemView.setOnClickListener(v -> listener.onCountryClick(country));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryName;
        TextView tvCapital;

        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.tv_country_name);
            tvCapital = itemView.findViewById(R.id.tv_capital);
        }
    }
}
