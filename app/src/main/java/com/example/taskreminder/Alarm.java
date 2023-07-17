package com.example.taskreminder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.media.Ringtone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Alarm extends AppCompatActivity {

    ImageView clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        ImageView clock = (ImageView) findViewById(R.id.clock);
        TextView title = (TextView) findViewById(R.id.event);
        Button dismissBtn = (Button) findViewById(R.id.dismissBtn);

        /*
        Bundle bundle = getIntent().getBundleExtra("data");
        String message = bundle.getString("alarmTitle");
        title.setText(message);
        */

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        animateClock(clock);

    }
    private void animateClock(ImageView clock) {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }

}