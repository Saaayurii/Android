package com.example.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private SeekBar seekBarLength;
    private TextView textLength;
    private CheckBox checkUppercase, checkLowercase, checkDigits, checkSymbols;
    private TextView textPassword, textStrength;
    private Button buttonGenerate, buttonCopy;
    private String lastPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBarLength = findViewById(R.id.seekBarLength);
        textLength = findViewById(R.id.textLength);
        checkUppercase = findViewById(R.id.checkUppercase);
        checkLowercase = findViewById(R.id.checkLowercase);
        checkDigits = findViewById(R.id.checkDigits);
        checkSymbols = findViewById(R.id.checkSymbols);
        textPassword = findViewById(R.id.textPassword);
        textStrength = findViewById(R.id.textStrength);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        buttonCopy = findViewById(R.id.buttonCopy);

        int initialLength = seekBarLength.getProgress() + 4;
        textLength.setText("Длина: " + initialLength + " символов");

        seekBarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textLength.setText("Длина: " + (progress + 4) + " символов");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        buttonGenerate.setOnClickListener(v -> generatePassword());

        buttonCopy.setOnClickListener(v -> {
            if (!lastPassword.isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(ClipData.newPlainText("password", lastPassword));
                Toast.makeText(this, "Скопировано в буфер обмена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generatePassword() {
        StringBuilder charset = new StringBuilder();
        if (checkUppercase.isChecked()) charset.append(UPPERCASE);
        if (checkLowercase.isChecked()) charset.append(LOWERCASE);
        if (checkDigits.isChecked()) charset.append(DIGITS);
        if (checkSymbols.isChecked()) charset.append(SYMBOLS);

        if (charset.length() == 0) {
            Toast.makeText(this, "Выберите хотя бы один тип символов", Toast.LENGTH_SHORT).show();
            return;
        }

        int length = seekBarLength.getProgress() + 4;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(charset.charAt(random.nextInt(charset.length())));
        }

        lastPassword = password.toString();
        textPassword.setText(lastPassword);
        buttonCopy.setEnabled(true);

        int score = 0;
        if (length >= 8) score++;
        if (length >= 12) score++;
        if (length >= 16) score++;
        if (checkUppercase.isChecked()) score++;
        if (checkLowercase.isChecked()) score++;
        if (checkDigits.isChecked()) score++;
        if (checkSymbols.isChecked()) score += 2;

        String strength;
        int color;
        if (score <= 2) { strength = "Надёжность: Слабый"; color = 0xFFFF0000; }
        else if (score <= 4) { strength = "Надёжность: Средний"; color = 0xFFFF8800; }
        else if (score <= 6) { strength = "Надёжность: Хороший"; color = 0xFF88BB00; }
        else { strength = "Надёжность: Очень надёжный"; color = 0xFF00AA00; }
        textStrength.setText(strength);
        textStrength.setTextColor(color);
    }
}
