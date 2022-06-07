package com.example.kg_cai;

import static com.example.kg_cai.SplashActivity.catList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {

    private GridView subjGridView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        subjGridView = findViewById(R.id.subj_gridview);
        toolbar = findViewById(R.id.toolbar_subject);

        Integer imageid[] = {
                R.drawable.number_logo,
                R.drawable.language_logo,
                R.drawable.filipino_logo,
                R.drawable.read_logo

        };

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Subjects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SubjGridAdapter adapter = new SubjGridAdapter(catList,imageid); //pass to the subjGridAdapter constructor\
        subjGridView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            SubjectActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}