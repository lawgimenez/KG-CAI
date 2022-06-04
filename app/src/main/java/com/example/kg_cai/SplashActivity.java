package com.example.kg_cai;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo = findViewById(R.id.imgLogo);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.logoanim);

        imgLogo.setAnimation(anim);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(3000);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }
}