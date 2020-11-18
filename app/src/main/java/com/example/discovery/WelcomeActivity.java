package com.example.discovery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.disocvery.R;

public class WelcomeActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        View root = (View) findViewById(R.id.root);
        root.setOnTouchListener((view, motionEvent) -> {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            return true;
        });
    }
}