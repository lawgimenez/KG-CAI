package com.example.kg_cai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvScore;
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        tvScore = findViewById(R.id.tv_result_score);
        btnDone = findViewById(R.id.btnDone);

        String score = getIntent().getStringExtra("SCORE"); //this is from QuestionActivity

        tvScore.setText(score);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
            }
        });
    }
}