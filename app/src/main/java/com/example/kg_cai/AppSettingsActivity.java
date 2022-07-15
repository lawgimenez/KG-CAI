package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.kg_cai.helpers.MyServiceMusic;

public class AppSettingsActivity extends AppCompatActivity {

    private Intent musicServiceIntent;
    private Switch switchMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        switchMusic = findViewById(R.id.switchMusic);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        musicServiceIntent = new Intent(getApplicationContext(), MyServiceMusic.class);

        switchMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchMusic.isChecked()){
                    startService(new Intent(getApplicationContext(), MyServiceMusic.class));
                }else{
                    stopService(new Intent(getApplicationContext(), MyServiceMusic.class));
                }
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