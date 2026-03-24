package com.example.productapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        String name = getIntent().getStringExtra("product_name");
        String description = getIntent().getStringExtra("product_description");
        double price = getIntent().getDoubleExtra("product_price", 0.0);

        TextView tvName = findViewById(R.id.tv_product_name);
        TextView tvDescription = findViewById(R.id.tv_product_description);
        TextView tvPrice = findViewById(R.id.tv_product_price);

        tvName.setText(name);
        tvDescription.setText(description);
        tvPrice.setText(String.format(getString(R.string.price_format), price));
    }
}
