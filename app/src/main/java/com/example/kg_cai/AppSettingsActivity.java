package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AppSettingsActivity extends AppCompatActivity {

    private Button btnMusicStart, btnMusicStop;
    private Intent musicServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);

        btnMusicStart = findViewById(R.id.btnMusicStart);
        btnMusicStop = findViewById(R.id.btnMusicStop);

        musicServiceIntent = new Intent(getApplicationContext(), MyServiceMusic.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMusicStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), MyServiceMusic.class));
            }
        });

        btnMusicStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), MyServiceMusic.class));
            }
        });
    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            AppSettingsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}