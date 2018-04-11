package com.example.app.jsdc;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

public class Opening extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        final RelativeLayout logoJSDC = (RelativeLayout) findViewById(R.id.logo);

        logoJSDC.setAlpha(0f);

        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(logoJSDC, "alpha", 3f, 0f);

        fadeAnim.setDuration(2500);

        fadeAnim.start();


        Thread timer = new Thread() {
            public void run() {
                try {

                    sleep(2300);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    PrefManager prefManager = new PrefManager(getApplicationContext());

                    // make first time launch TRUE
                    prefManager.setFirstTimeLaunch(true);

                    startActivity(new Intent(Opening.this, ScanQR.class));
                    finish();


                }
            }
        };
        timer.start();


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}