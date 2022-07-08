package com.example.kg_cai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainSoundsActivity extends AppCompatActivity {

    RelativeLayout color, number, alphabet, week, shape;
    ImageView backButton;
    LinearLayout video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sounds_main);

        color = findViewById(R.id.color);
        number = findViewById(R.id.number);
        alphabet = findViewById(R.id.alphabet);
        week = findViewById(R.id.week);
        shape = findViewById(R.id.shape);
        video = findViewById(R.id.btnvideo);
        backButton = findViewById(R.id.btnSoundsBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSoundsActivity.this, MainActivity.class));
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSoundsActivity.this, ColorsActivity.class));
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSoundsActivity.this, NumbersActivity.class));
            }
        });

        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSoundsActivity.this, AlphabetActivity.class));
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSoundsActivity.this, WeekActivity.class));
            }
        });

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSoundsActivity.this, ShapesActivity.class));
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSoundsActivity.this, MainVideosActivity.class));
            }
        });


    }

}