package com.example.booksapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "BooksPrefs";
    private static final String KEY_FAVORITES = "favorites";

    private Set<String> favorites;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        favorites = new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<>()));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView textError = findViewById(R.id.textError);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);

        ApiService service = RetrofitClient.getInstance().create(ApiService.class);
        service.searchBooks("android programming", 20).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> books = response.body().getDocs();
                    if (books == null) books = new ArrayList<>();
                    recyclerView.setAdapter(new BookAdapter(books, favorites, (book, wasFavorite) -> {
                        if (wasFavorite) {
                            Toast.makeText(MainActivity.this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
                        }
                        saveFavorites();
                    }));
                } else {
                    textError.setVisibility(View.VISIBLE);
                    textError.setText("Ошибка загрузки данных");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                textError.setVisibility(View.VISIBLE);
                textError.setText("Ошибка подключения: " + t.getMessage());
            }
        });
    }

    private void saveFavorites() {
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }
}
