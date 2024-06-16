package com.example.momtobe;


import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView gifImageView = findViewById(R.id.gifImageView);

        Glide.with(this)
                .asGif()
                .load(R.raw.momtobe)
                .into(gifImageView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, RegistrationActivity.class);
                    startActivity(i);
                    finish();
                }
                // Start the main activity
//                Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(i);
//
//                // Close this activity
//                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
