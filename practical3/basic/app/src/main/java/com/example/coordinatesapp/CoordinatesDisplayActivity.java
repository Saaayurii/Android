package com.example.coordinatesapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CoordinatesDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinates_display);

        String x = getIntent().getStringExtra("coord_x");
        String y = getIntent().getStringExtra("coord_y");

        TextView tvCoordinates = findViewById(R.id.tv_coordinates);
        tvCoordinates.setText(getString(R.string.coordinates_format, x, y));
    }
}
