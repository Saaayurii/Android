package com.example.countriesadvanced;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CountryAdapter.OnCountryClickListener {

    private static final int REQUEST_ADD_COUNTRY = 1001;

    private List<Country> allCountries = new ArrayList<>();
    private List<Country> filteredCountries = new ArrayList<>();
    private CountryAdapter adapter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        filteredCountries.addAll(allCountries);
        adapter = new CountryAdapter(filteredCountries, this);
        recyclerView.setAdapter(adapter);

        etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCountries(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCountryActivity.class);
            startActivityForResult(intent, REQUEST_ADD_COUNTRY);
        });
    }

    private void initData() {
        allCountries.add(new Country("Україна", "Київ", 41000000, 603550.0));
        allCountries.add(new Country("Франція", "Париж", 67000000, 551695.0));
        allCountries.add(new Country("Німеччина", "Берлін", 83000000, 357114.0));
        allCountries.add(new Country("Японія", "Токіо", 125000000, 377975.0));
        allCountries.add(new Country("США", "Вашингтон", 331000000, 9833517.0));
        allCountries.add(new Country("Велика Британія", "Лондон", 67000000, 242495.0));
        allCountries.add(new Country("Канада", "Оттава", 38000000, 9984670.0));
        allCountries.add(new Country("Австралія", "Канберра", 26000000, 7692024.0));
        allCountries.add(new Country("Китай", "Пекін", 1412000000, 9596960.0));
        allCountries.add(new Country("Бразилія", "Бразиліа", 214000000, 8515767.0));
    }

    private void filterCountries(String query) {
        filteredCountries.clear();
        if (query.isEmpty()) {
            filteredCountries.addAll(allCountries);
        } else {
            String lowerQuery = query.toLowerCase();
            for (Country c : allCountries) {
                if (c.getName().toLowerCase().contains(lowerQuery) ||
                        c.getCapital().toLowerCase().contains(lowerQuery)) {
                    filteredCountries.add(c);
                }
            }
        }
        adapter.updateList(filteredCountries);
    }

    @Override
    public void onCountryClick(Country country) {
        Intent intent = new Intent(this, CountryDetailActivity.class);
        intent.putExtra("country", country);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_COUNTRY && resultCode == RESULT_OK && data != null) {
            Country newCountry = (Country) data.getSerializableExtra("new_country");
            if (newCountry != null) {
                allCountries.add(newCountry);
                filterCountries(etSearch.getText().toString());
                Toast.makeText(this, getString(R.string.country_added), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
