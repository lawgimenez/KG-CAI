package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.kg_cai.adapter.ScoreAdapter;
import com.example.kg_cai.helpers.ScoreDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardsActivity extends AppCompatActivity {

    private RecyclerView leaderboardRv;
    private ProgressBar progressBar;
    List<ScoreDataModel> list;

    ScoreAdapter scoreAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        leaderboardRv = findViewById(R.id.leaderboardRv);
        progressBar = findViewById(R.id.leaderboardProgressboard);

        Toolbar toolbar = findViewById(R.id.toolbar_leaderboards);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Leaderboards");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Score");
        list = new ArrayList<ScoreDataModel>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        leaderboardRv.setLayoutManager(manager);
        leaderboardRv.setHasFixedSize(true);


        databaseReference.orderByChild("overallScore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ScoreDataModel scoreDataModel = dataSnapshot.getValue(ScoreDataModel.class);
                    list.add(scoreDataModel);
                }
                scoreAdapter = new ScoreAdapter(list,LeaderboardsActivity.this);
                leaderboardRv.setAdapter(scoreAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int sum = 0;
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                    Object score = map.get("Score");
//                    int value = Integer.parseInt(String.valueOf(score));
//                    sum += value;
//
//                    list.add(sum);
//                }
//                scoreAdapter = new ScoreAdapter(list,LeaderboardsActivity.this);
//                leaderboardRv.setAdapter(scoreAdapter);
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            LeaderboardsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}