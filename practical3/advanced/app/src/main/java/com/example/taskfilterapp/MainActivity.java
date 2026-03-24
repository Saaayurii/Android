package com.example.taskfilterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbWork;
    private CheckBox cbHome;
    private CheckBox cbSport;
    private Button btnSaveAndView;

    private static final String PREFS_NAME = "task_filter_prefs";
    static final String KEY_WORK = "cat_work";
    static final String KEY_HOME = "cat_home";
    static final String KEY_SPORT = "cat_sport";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbWork = findViewById(R.id.cb_work);
        cbHome = findViewById(R.id.cb_home);
        cbSport = findViewById(R.id.cb_sport);
        btnSaveAndView = findViewById(R.id.btn_save_view);

        // Restore saved state
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        cbWork.setChecked(prefs.getBoolean(KEY_WORK, false));
        cbHome.setChecked(prefs.getBoolean(KEY_HOME, false));
        cbSport.setChecked(prefs.getBoolean(KEY_SPORT, false));

        btnSaveAndView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(KEY_WORK, cbWork.isChecked());
                editor.putBoolean(KEY_HOME, cbHome.isChecked());
                editor.putBoolean(KEY_SPORT, cbSport.isChecked());
                editor.apply();

                Intent intent = new Intent(MainActivity.this, FilteredTasksActivity.class);
                startActivity(intent);
            }
        });
    }
}
