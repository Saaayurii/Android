package com.example.countdowntimer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView textTargetDate;
    private TextView textCountdown;
    private Button buttonStart;
    private Button buttonStop;
    private CountDownTimer countDownTimer;
    private long targetTimeMillis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTargetDate = findViewById(R.id.textTargetDate);
        textCountdown = findViewById(R.id.textCountdown);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        Button buttonPickDate = findViewById(R.id.buttonPickDate);

        buttonPickDate.setOnClickListener(v -> pickDateTime());
        buttonStart.setOnClickListener(v -> startCountdown());
        buttonStop.setOnClickListener(v -> stopCountdown());
    }

    private void pickDateTime() {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            new TimePickerDialog(this, (tView, hour, minute) -> {
                Calendar target = Calendar.getInstance();
                target.set(year, month, day, hour, minute, 0);
                target.set(Calendar.MILLISECOND, 0);
                targetTimeMillis = target.getTimeInMillis();
                String dateStr = String.format("%02d/%02d/%04d %02d:%02d", day, month + 1, year, hour, minute);
                textTargetDate.setText("Событие: " + dateStr);
                buttonStart.setEnabled(true);
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void startCountdown() {
        long now = System.currentTimeMillis();
        long diff = targetTimeMillis - now;
        if (diff <= 0) {
            Toast.makeText(this, "Выбранное время уже прошло!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (countDownTimer != null) countDownTimer.cancel();
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);

        countDownTimer = new CountDownTimer(diff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = millisUntilFinished / (1000 * 60 * 60 * 24);
                long hours = (millisUntilFinished % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60);
                long seconds = (millisUntilFinished % (1000 * 60)) / 1000;
                textCountdown.setText(String.format("%d дн. %02d:%02d:%02d", days, hours, minutes, seconds));
            }

            @Override
            public void onFinish() {
                textCountdown.setText("Время вышло!");
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                Toast.makeText(MainActivity.this, "Событие наступило!", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        textCountdown.setText("Остановлен");
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
