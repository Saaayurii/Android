package com.example.buttonrotate;

import android.os.Bundle;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.animatedButton);

        RotateAnimation rotate = new RotateAnimation(
                0f, 360f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setFillAfter(false);

        ScaleAnimation scale = new ScaleAnimation(
                1f, 1.3f,
                1f, 1.3f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(250);
        scale.setRepeatCount(1);
        scale.setRepeatMode(android.view.animation.Animation.REVERSE);

        AnimationSet animSet = new AnimationSet(false);
        animSet.addAnimation(rotate);
        animSet.addAnimation(scale);

        button.setOnClickListener(v -> button.startAnimation(animSet));
    }
}
