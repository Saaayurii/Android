package com.example.studentsapp;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private StudentAdapter adapter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        adapter = new StudentAdapter(student -> executor.execute(() -> db.studentDao().delete(student)));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        db.studentDao().getAllStudents().observe(this, students -> adapter.setStudents(students));

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(48, 16, 48, 16);

        EditText editName = new EditText(this);
        editName.setHint("Имя студента");
        layout.addView(editName);

        EditText editGrade = new EditText(this);
        editGrade.setHint("Оценка (1-10)");
        editGrade.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(editGrade);

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Добавить студента")
                .setView(layout)
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = editName.getText().toString().trim();
                    String gradeStr = editGrade.getText().toString().trim();
                    if (name.isEmpty() || gradeStr.isEmpty()) {
                        Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        float grade = Float.parseFloat(gradeStr);
                        if (grade < 1 || grade > 10) {
                            Toast.makeText(this, "Оценка должна быть от 1 до 10", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Student student = new Student(name, grade);
                        executor.execute(() -> db.studentDao().insert(student));
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Некорректная оценка", Toast.LENGTH_SHORT).show();
                    }
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
