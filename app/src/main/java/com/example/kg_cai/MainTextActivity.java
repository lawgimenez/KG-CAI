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

public class MainTextActivity extends AppCompatActivity {

    private RelativeLayout btnLanguageText, btnNumeracyText, btnFilipinoText,btnReadingsText;
    private Button btnTextMiniGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_text);

        btnLanguageText = findViewById(R.id.btnLanguageText);
        btnNumeracyText = findViewById(R.id.btnNumeracyText);
        btnFilipinoText = findViewById(R.id.btnFilipinoText);
        btnReadingsText = findViewById(R.id.btnReadingsText);
        btnTextMiniGame = findViewById(R.id.btnTextMiniGame);

        Toolbar toolbar = findViewById(R.id.toolbar_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTextMiniGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TextRecognitionActivity.class));
            }
        });

        btnLanguageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextActivity.class); //pass to text activity
                intent.putExtra("txtFolder", "LanguageLiteracy_Text");
                startActivity(intent);
            }
        });

        btnNumeracyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextActivity.class); //pass to text activity
                intent.putExtra("txtFolder", "Numeracy_Text");
                startActivity(intent);
            }
        });

        btnFilipinoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextActivity.class); //pass to text activity
                intent.putExtra("txtFolder", "Filipino_Text");
                startActivity(intent);
            }
        });

        btnReadingsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextActivity.class); //pass to text activity
                intent.putExtra("txtFolder", "Readings_Text");
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            MainTextActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}