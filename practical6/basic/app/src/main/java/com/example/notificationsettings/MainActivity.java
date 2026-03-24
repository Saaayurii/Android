package com.example.notificationsettings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "NotificationPrefs";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";

    private Switch switchNotifications;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchNotifications = findViewById(R.id.switchNotifications);
        textViewStatus = findViewById(R.id.textViewStatus);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Load saved settings
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean notificationsEnabled = prefs.getBoolean(KEY_NOTIFICATIONS, false);
        switchNotifications.setChecked(notificationsEnabled);
        updateStatus(notificationsEnabled);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = switchNotifications.isChecked();
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(KEY_NOTIFICATIONS, enabled);
                editor.apply();
                updateStatus(enabled);
                Toast.makeText(MainActivity.this, "Настройки сохранены", Toast.LENGTH_SHORT).show();
            }
        });

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> updateStatus(isChecked));
    }

    private void updateStatus(boolean enabled) {
        if (enabled) {
            textViewStatus.setText("Статус: уведомления включены");
        } else {
            textViewStatus.setText("Статус: уведомления выключены");
        }
    }
}
