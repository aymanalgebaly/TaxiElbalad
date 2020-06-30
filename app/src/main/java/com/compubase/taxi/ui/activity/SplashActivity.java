package com.compubase.taxi.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.compubase.taxi.R;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private boolean login_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        login_user = preferences.getBoolean("login", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (login_user) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);

        ImageView imageView = findViewById(R.id.img_splash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        imageView.setAnimation(animation);



    }
}
