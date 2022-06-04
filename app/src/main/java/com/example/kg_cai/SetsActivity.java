package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

public class SetsActivity extends AppCompatActivity {

    private Toolbar setsToolbar;
    private GridView setsGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        setsToolbar = findViewById(R.id.setsToolbar);
        setsGridView = findViewById(R.id.setsGridView);

        String title = getIntent().getStringExtra("SUBJECT_PICKED");

        setSupportActionBar(setsToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SetsAdapter adapter = new SetsAdapter("Quiz", 3); //pass to the constructor of setsAdapter

        setsGridView.setAdapter(adapter);

    }












    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}