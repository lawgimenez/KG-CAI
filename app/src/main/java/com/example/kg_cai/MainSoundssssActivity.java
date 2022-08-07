package com.example.kg_cai;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kg_cai.helpers.MyServiceMusic;

public class MainSoundssssActivity extends AppCompatActivity {

    RelativeLayout color, number, alphabet, week, shape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_soundsssssssssssssssssss_main);

        color = findViewById(R.id.color);
        number = findViewById(R.id.number);
        alphabet = findViewById(R.id.alphabet);
        week = findViewById(R.id.week);
        shape = findViewById(R.id.shape);

        Toolbar toolbar = findViewById(R.id.toolbar_sound);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service for background music use to start
                Intent musicServiceIntent = new Intent(getApplicationContext(), MyServiceMusic.class);
                stopService(new Intent(getApplicationContext(), MyServiceMusic.class));
                startActivity(new Intent(MainSoundssssActivity.this, ColorsActivity.class));
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service for background music use to start
                Intent musicServiceIntent = new Intent(getApplicationContext(), MyServiceMusic.class);
                stopService(new Intent(getApplicationContext(), MyServiceMusic.class));
                startActivity(new Intent(MainSoundssssActivity.this, NumbersActivity.class));
            }
        });

        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service for background music use to start
                Intent musicServiceIntent = new Intent(getApplicationContext(), MyServiceMusic.class);
                stopService(new Intent(getApplicationContext(), MyServiceMusic.class));
                startActivity(new Intent(MainSoundssssActivity.this, AlphabetActivity.class));
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service for background music use to start
                Intent musicServiceIntent = new Intent(getApplicationContext(), MyServiceMusic.class);
                stopService(new Intent(getApplicationContext(), MyServiceMusic.class));
                startActivity(new Intent(MainSoundssssActivity.this, WeekActivity.class));
            }
        });

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service for background music use to start
                Intent musicServiceIntent = new Intent(getApplicationContext(), MyServiceMusic.class);
                stopService(new Intent(getApplicationContext(), MyServiceMusic.class));
                startActivity(new Intent(MainSoundssssActivity.this, ShapesActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            MainSoundssssActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        MainSoundssssActivity.this.finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}