package com.example.booksapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Set;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Book book, boolean isFavorite);
    }

    private final List<Book> books;
    private final Set<String> favorites;
    private final OnFavoriteClickListener listener;

    public BookAdapter(List<Book> books, Set<String> favorites, OnFavoriteClickListener listener) {
        this.books = books;
        this.favorites = favorites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.textTitle.setText(book.getTitle());
        holder.textAuthor.setText(book.getAuthors());
        holder.textYear.setText(book.getYear());

        boolean isFav = favorites.contains(book.getKey());
        holder.buttonFavorite.setText(isFav ? "\u2605" : "\u2606");
        holder.buttonFavorite.setOnClickListener(v -> {
            boolean nowFav = favorites.contains(book.getKey());
            listener.onFavoriteClick(book, nowFav);
            if (nowFav) favorites.remove(book.getKey());
            else favorites.add(book.getKey());
            holder.buttonFavorite.setText(favorites.contains(book.getKey()) ? "\u2605" : "\u2606");
        });
    }

    @Override
    public int getItemCount() { return books.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textAuthor, textYear;
        Button buttonFavorite;
        ViewHolder(View view) {
            super(view);
            textTitle = view.findViewById(R.id.textTitle);
            textAuthor = view.findViewById(R.id.textAuthor);
            textYear = view.findViewById(R.id.textYear);
            buttonFavorite = view.findViewById(R.id.buttonFavorite);
        }
    }
}
