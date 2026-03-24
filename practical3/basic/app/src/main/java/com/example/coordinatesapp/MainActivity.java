package com.example.coordinatesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etX;
    private EditText etY;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etX = findViewById(R.id.et_x);
        etY = findViewById(R.id.et_y);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xStr = etX.getText().toString().trim();
                String yStr = etY.getText().toString().trim();

                if (xStr.isEmpty() || yStr.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.enter_coordinates), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, CoordinatesDisplayActivity.class);
                intent.putExtra("coord_x", xStr);
                intent.putExtra("coord_y", yStr);
                startActivity(intent);
            }
        });
    }
}
