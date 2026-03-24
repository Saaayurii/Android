package com.example.countriesapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Country> countries = new ArrayList<>();
        countries.add(new Country("Україна", "Київ"));
        countries.add(new Country("Франція", "Париж"));
        countries.add(new Country("Німеччина", "Берлін"));
        countries.add(new Country("Японія", "Токіо"));
        countries.add(new Country("США", "Вашингтон"));
        countries.add(new Country("Велика Британія", "Лондон"));
        countries.add(new Country("Канада", "Оттава"));
        countries.add(new Country("Австралія", "Канберра"));
        countries.add(new Country("Китай", "Пекін"));
        countries.add(new Country("Бразилія", "Бразиліа"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CountryAdapter(countries));
    }
}
