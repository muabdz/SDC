package com.example.app.sdc;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.app.sdc.Utils.SessionManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final RelativeLayout splash = (RelativeLayout) findViewById(R.id.activity_opening);

        splash.setAlpha(0f);

        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(splash, "alpha", 3f, 0f);

        fadeAnim.setDuration(2500);

        fadeAnim.start();


        Thread timer = new Thread() {
            public void run() {
                try {

                    sleep(2300);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    SessionManager sessionManager = new SessionManager(getApplicationContext());

                    // make first time launch TRUE
                    sessionManager.setFirstTimeLaunch(true);
                    String loggedIn = sessionManager.getUid();
                    if (loggedIn.isEmpty()){
                        startActivity(new Intent(SplashScreen.this, LoginPengujiKode.class));
                        finish();
                    }else{
                        startActivity(new Intent(SplashScreen.this, LoginPeserta.class));
                        finish();
                    }
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