package com.example.productapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        PRODUCTS.add(new Product("Телефон", "Смартфон с большим экраном и мощным процессором", 12999.99));
        PRODUCTS.add(new Product("Ноутбук", "Ноутбук для работы и учёбы с SSD накопителем", 34999.99));
        PRODUCTS.add(new Product("Планшет", "Планшет для чтения и просмотра видео", 9999.99));
        PRODUCTS.add(new Product("Наушники", "Беспроводные наушники с шумоподавлением", 4999.99));
        PRODUCTS.add(new Product("Умные часы", "Смарт-часы с мониторингом здоровья", 7499.99));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.product_container);

        for (final Product product : PRODUCTS) {
            Button btn = new Button(this);
            btn.setText(product.getName());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            btn.setLayoutParams(params);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                    intent.putExtra("product_name", product.getName());
                    intent.putExtra("product_description", product.getDescription());
                    intent.putExtra("product_price", product.getPrice());
                    startActivity(intent);
                }
            });
            container.addView(btn);
        }
    }
}
