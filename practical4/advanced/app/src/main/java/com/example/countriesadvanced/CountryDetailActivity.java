package com.example.countriesadvanced;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CountryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        Country country = (Country) getIntent().getSerializableExtra("country");
        if (country == null) {
            finish();
            return;
        }

        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvCapital = findViewById(R.id.tv_detail_capital);
        TextView tvPopulation = findViewById(R.id.tv_detail_population);
        TextView tvArea = findViewById(R.id.tv_detail_area);

        tvName.setText(country.getName());
        tvCapital.setText(getString(R.string.capital_detail, country.getCapital()));
        tvPopulation.setText(getString(R.string.population_detail, country.getPopulation()));
        tvArea.setText(getString(R.string.area_detail, country.getArea()));
    }
}
