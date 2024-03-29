package com.example.notetome2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class Splash extends Activity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);

                finish();

            }
        }, SPLASH_TIME_OUT);
    }

}
