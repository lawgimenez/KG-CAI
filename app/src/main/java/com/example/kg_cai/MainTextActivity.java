package com.example.kg_cai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

        btnTextMiniGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TextRecognition.class));
            }
        });


    }
}