package com.example.skygreen.pesonapurworejo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private int waktuDelay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent   = new Intent(SplashScreen.this, WalkthroughActivity.class);
                startActivity(intent);
                finish();
            }
        }, waktuDelay);
    }
}
