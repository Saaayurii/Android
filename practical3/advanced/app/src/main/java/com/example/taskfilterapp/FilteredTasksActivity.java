package com.example.taskfilterapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class FilteredTasksActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "task_filter_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_tasks);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean showWork = prefs.getBoolean(MainActivity.KEY_WORK, false);
        boolean showHome = prefs.getBoolean(MainActivity.KEY_HOME, false);
        boolean showSport = prefs.getBoolean(MainActivity.KEY_SPORT, false);

        LinearLayout container = findViewById(R.id.tasks_container);
        List<String> tasksToShow = new ArrayList<>();

        if (showWork) {
            tasksToShow.add("[Работа] Подготовить отчёт");
            tasksToShow.add("[Работа] Провести встречу с командой");
            tasksToShow.add("[Работа] Проверить электронную почту");
        }
        if (showHome) {
            tasksToShow.add("[Дом] Убрать квартиру");
            tasksToShow.add("[Дом] Купить продукты");
            tasksToShow.add("[Дом] Приготовить ужин");
        }
        if (showSport) {
            tasksToShow.add("[Спорт] Пробежка 5 км");
            tasksToShow.add("[Спорт] Тренировка в зале");
            tasksToShow.add("[Спорт] Растяжка и йога");
        }

        if (tasksToShow.isEmpty()) {
            TextView tvEmpty = new TextView(this);
            tvEmpty.setText(getString(R.string.no_tasks_selected));
            tvEmpty.setTextSize(18f);
            container.addView(tvEmpty);
        } else {
            for (String task : tasksToShow) {
                TextView tv = new TextView(this);
                tv.setText(task);
                tv.setTextSize(16f);
                tv.setPadding(0, 12, 0, 12);
                container.addView(tv);
            }
        }
    }
}
