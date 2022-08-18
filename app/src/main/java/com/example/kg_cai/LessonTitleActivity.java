package com.example.kg_cai;

import static com.example.kg_cai.adapter.SubjectAdapter.subject_picked;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kg_cai.adapter.LessonTitleAdapter;
import com.example.kg_cai.helpers.LessonTitleModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class LessonTitleActivity extends AppCompatActivity {
    private RecyclerView lessonTitleRv;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;

    LessonTitleAdapter lessonTitleAdapter;
    List<LessonTitleModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_title);

        lessonTitleRv = findViewById(R.id.lessonTitleRv);

        Toolbar toolbar = findViewById(R.id.toolbar_subjTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(subject_picked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference().child("Subjects").child(subject_picked).child("Lessons"); //folder in realtime db

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        lessonTitleRv.setLayoutManager(manager);
        lessonTitleRv.setHasFixedSize(true);
        list = new ArrayList<>();

        mReference.orderByChild("Title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    LessonTitleModel lessonTitleModel = dataSnapshot.getValue(LessonTitleModel.class);
                    list.add(lessonTitleModel);
                }
                lessonTitleAdapter = new LessonTitleAdapter(list, LessonTitleActivity.this);
                lessonTitleRv.setAdapter(lessonTitleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onBackPressed() {
        LessonTitleActivity.this.finish();
        startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            LessonTitleActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}