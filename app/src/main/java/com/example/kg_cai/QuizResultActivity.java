package com.example.kg_cai;

import static com.example.kg_cai.QuestionActivity.setNo;
import static com.example.kg_cai.SetsActivity.setsIDs;
import static com.example.kg_cai.SplashActivity.catList;
import static com.example.kg_cai.SplashActivity.selected_cat_index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvScore;
    private Button btnDone;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String score, over;
    int modified_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tvScore = findViewById(R.id.tv_result_score);
        btnDone = findViewById(R.id.btnDone);

        score = getIntent().getStringExtra("SCORE"); //this is from QuestionActivity
        modified_score = Integer.parseInt(score);
        over = getIntent().getStringExtra("SCORE_OVER");

        tvScore.setText(score + "/" + over);

        databaseReference.child("Score").child(firebaseUser.getUid()).child(catList.get(selected_cat_index).getName()+"Sets").child("Set"+setsIDs.get(setNo)).child("score")
                .setValue(score)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data added", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { //score will be stored in realtime db in specific subject name ex: Numeracy score.
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                QuizResultActivity.this.finish();
            }
        });

    }
}