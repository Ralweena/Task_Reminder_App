package com.example.taskreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, categories.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}