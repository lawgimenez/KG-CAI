package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainVideosActivity extends AppCompatActivity {

    private RelativeLayout btnNumeracy, btnLanguageLiteracy, btnFilipino, btnReadings;
    private Button btnQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_videos);

        btnNumeracy = findViewById(R.id.btnVideoAdminNumeracy);
        btnFilipino = findViewById(R.id.btnVideoFilipino);
        btnReadings = findViewById(R.id.btnVideoReading);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnLanguageLiteracy = findViewById(R.id.btnVideoLanguageLiteracy);

        Toolbar toolbar = findViewById(R.id.toolbar_videos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnNumeracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideosActivity.class);
                intent.putExtra("VideoFolder", "NumeracyVideos");
                startActivity(intent);
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
            }
        });

        btnFilipino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideosActivity.class);
                intent.putExtra("VideoFolder", "FilipinoVideos");
                startActivity(intent);
            }
        });

        btnReadings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideosActivity.class);
                intent.putExtra("VideoFolder", "ReadingsVideos");
                startActivity(intent);
            }
        });

        btnLanguageLiteracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideosActivity.class);
                intent.putExtra("VideoFolder", "LanguageLiteracyVideos");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            MainVideosActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}