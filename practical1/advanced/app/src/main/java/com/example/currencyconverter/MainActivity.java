package com.example.currencyconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etAmount;
    private Spinner spinnerCurrency;
    private Button btnConvert;
    private TextView tvResult;

    // Rates: 1 UAH = X currency
    private static final double[] RATES = {0.024, 0.022, 0.019, 3.62};
    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY"};

    private int selectedCurrencyIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.et_amount);
        spinnerCurrency = findViewById(R.id.spinner_currency);
        btnConvert = findViewById(R.id.btn_convert);
        tvResult = findViewById(R.id.tv_result);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                CURRENCIES
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapter);

        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCurrencyIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        String amountStr = etAmount.getText().toString().trim();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_amount), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            double rate = RATES[selectedCurrencyIndex];
            double result = amount * rate;
            String currency = CURRENCIES[selectedCurrencyIndex];

            String resultText = String.format(
                    getString(R.string.result_format),
                    amount, "UAH", result, currency
            );
            tvResult.setText(resultText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_number), Toast.LENGTH_SHORT).show();
        }
    }
}
