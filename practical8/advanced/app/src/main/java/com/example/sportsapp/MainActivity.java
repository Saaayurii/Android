package com.example.sportsapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private AchievementAdapter adapter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Spinner spinnerFilter;
    private final List<String> sportTypes = new ArrayList<>(Arrays.asList("Все виды"));
    private ArrayAdapter<String> spinnerAdapter;
    private String currentFilter = "Все виды";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        adapter = new AchievementAdapter(a -> executor.execute(() -> db.achievementDao().delete(a)));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        spinnerFilter = findViewById(R.id.spinnerFilter);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sportTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentFilter = sportTypes.get(position);
                loadAchievements();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        db.achievementDao().getAllSportTypes().observe(this, types -> {
            sportTypes.clear();
            sportTypes.add("Все виды");
            if (types != null) sportTypes.addAll(types);
            spinnerAdapter.notifyDataSetChanged();
        });

        loadAchievements();

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> showAddDialog());
    }

    private void loadAchievements() {
        if ("Все виды".equals(currentFilter)) {
            db.achievementDao().getAllSortedByDate().observe(this, items -> adapter.setItems(items));
        } else {
            db.achievementDao().getBySport(currentFilter).observe(this, items -> adapter.setItems(items));
        }
    }

    private void showAddDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 16, 48, 16);

        EditText editTitle = new EditText(this);
        editTitle.setHint("Название достижения");
        layout.addView(editTitle);

        EditText editSport = new EditText(this);
        editSport.setHint("Вид спорта");
        layout.addView(editSport);

        EditText editDesc = new EditText(this);
        editDesc.setHint("Описание (необязательно)");
        layout.addView(editDesc);

        final String[] selectedDate = {""};
        Button dateButton = new Button(this);
        dateButton.setText("Выбрать дату тренировки");
        layout.addView(dateButton);
        dateButton.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                selectedDate[0] = String.format("%04d-%02d-%02d", year, month + 1, day);
                dateButton.setText("Дата: " + selectedDate[0]);
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
        });

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Добавить достижение")
                .setView(layout)
                .setPositiveButton("OK", (dialog, which) -> {
                    String title = editTitle.getText().toString().trim();
                    String sport = editSport.getText().toString().trim();
                    String desc = editDesc.getText().toString().trim();
                    if (title.isEmpty() || sport.isEmpty() || selectedDate[0].isEmpty()) {
                        Toast.makeText(this, "Заполните название, вид спорта и выберите дату", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Achievement a = new Achievement(title, sport, selectedDate[0], desc);
                    executor.execute(() -> db.achievementDao().insert(a));
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
