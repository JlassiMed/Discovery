package com.example.disocvery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        View mylayt=(View)findViewById(R.id.myLayout);
        mylayt.setOnTouchListener(new View.OnTouchListener(){


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                return true;
            }
        });
    }
}