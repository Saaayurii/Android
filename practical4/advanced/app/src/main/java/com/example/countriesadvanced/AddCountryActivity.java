package com.example.countriesadvanced;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCountryActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etCapital;
    private EditText etPopulation;
    private EditText etArea;
    private Button btnAdd;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);

        etName = findViewById(R.id.et_country_name);
        etCapital = findViewById(R.id.et_capital);
        etPopulation = findViewById(R.id.et_population);
        etArea = findViewById(R.id.et_area);
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCountry();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void addCountry() {
        String name = etName.getText().toString().trim();
        String capital = etCapital.getText().toString().trim();
        String populationStr = etPopulation.getText().toString().trim();
        String areaStr = etArea.getText().toString().trim();

        if (name.isEmpty() || capital.isEmpty() || populationStr.isEmpty() || areaStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            long population = Long.parseLong(populationStr);
            double area = Double.parseDouble(areaStr);

            Country newCountry = new Country(name, capital, population, area);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_country", newCountry);
            setResult(RESULT_OK, resultIntent);
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_number), Toast.LENGTH_SHORT).show();
        }
    }
}
