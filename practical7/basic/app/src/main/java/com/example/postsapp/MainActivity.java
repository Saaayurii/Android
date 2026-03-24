package com.example.postsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);

        ApiService service = RetrofitClient.getInstance().create(ApiService.class);
        service.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> posts = response.body();
                    int count = Math.min(posts.size(), 20);
                    recyclerView.setAdapter(new PostAdapter(posts.subList(0, count)));
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
