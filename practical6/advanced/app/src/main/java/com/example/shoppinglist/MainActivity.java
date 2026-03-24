package com.example.shoppinglist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "ShoppingPrefs";
    private static final String KEY_LIST = "purchase_list";
    private static final String SEPARATOR = ";;";

    private List<Purchase> purchases;
    private PurchaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        purchases = loadPurchases();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PurchaseAdapter(purchases, new PurchaseAdapter.OnItemActionListener() {
            @Override
            public void onEdit(int position) {
                showEditDialog(position);
            }
            @Override
            public void onDelete(int position) {
                purchases.remove(position);
                adapter.notifyItemRemoved(position);
                savePurchases();
            }
        });
        recyclerView.setAdapter(adapter);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        showPurchaseDialog("Добавить товар", null, -1);
    }

    private void showEditDialog(int position) {
        showPurchaseDialog("Редактировать товар", purchases.get(position), position);
    }

    private void showPurchaseDialog(String title, Purchase existing, int position) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 16, 48, 16);

        EditText editName = new EditText(this);
        editName.setHint("Название товара");
        if (existing != null) editName.setText(existing.getName());
        layout.addView(editName);

        EditText editPrice = new EditText(this);
        editPrice.setHint("Цена (руб.)");
        editPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        if (existing != null) editPrice.setText(String.valueOf(existing.getPrice()));
        layout.addView(editPrice);

        EditText editQty = new EditText(this);
        editQty.setHint("Количество");
        editQty.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (existing != null) editQty.setText(String.valueOf(existing.getQuantity()));
        layout.addView(editQty);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(layout)
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String priceStr = editPrice.getText().toString().trim();
                    String qtyStr = editQty.getText().toString().trim();
                    if (name.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
                        Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        double price = Double.parseDouble(priceStr);
                        int qty = Integer.parseInt(qtyStr);
                        if (price < 0 || qty < 0) {
                            Toast.makeText(this, "Цена и количество должны быть положительными", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Purchase p = new Purchase(name, price, qty);
                        if (position >= 0) {
                            purchases.set(position, p);
                            adapter.notifyItemChanged(position);
                        } else {
                            purchases.add(p);
                            adapter.notifyItemInserted(purchases.size() - 1);
                        }
                        savePurchases();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Некорректные данные", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void savePurchases() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < purchases.size(); i++) {
            if (i > 0) sb.append(SEPARATOR);
            sb.append(purchases.get(i).toStorageString());
        }
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                .putString(KEY_LIST, sb.toString())
                .apply();
    }

    private List<Purchase> loadPurchases() {
        List<Purchase> list = new ArrayList<>();
        String data = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(KEY_LIST, "");
        if (!data.isEmpty()) {
            for (String s : data.split(";;")) {
                Purchase p = Purchase.fromStorageString(s);
                if (p != null) list.add(p);
            }
        }
        return list;
    }
}
