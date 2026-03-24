package com.example.numberdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private int currentNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        Button buttonInput = findViewById(R.id.buttonInput);
        Button buttonReset = findViewById(R.id.buttonReset);

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberDialog();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 0;
                textViewResult.setText("Результат: 0");
            }
        });
    }

    private void showNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ввод числа");
        builder.setMessage("Введите число:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        input.setHint("Введите целое число");
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString().trim();
                if (text.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Поле не должно быть пустым", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int number = Integer.parseInt(text);
                    currentNumber = number + 10;
                    textViewResult.setText("Результат: " + currentNumber + " (" + number + " + 10)");
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Введите корректное число", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
