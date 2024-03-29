package com.example.kg_cai;

import static com.example.kg_cai.adapter.SubjectAdapter.subject_picked;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

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
    private VideoView videoMain;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;

    LessonTitleAdapter lessonTitleAdapter;
    List<LessonTitleModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_title);
        playBackgroundVideo();

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

    private void playBackgroundVideo() {
        videoMain = findViewById(R.id.lessonTitleVideoMain);
        String path = "android.resource://"+getPackageName()+"/"+R.raw.secondary_bg;
        Uri uri = Uri.parse(path);
        videoMain.setVideoURI(uri);
        videoMain.start();

        videoMain.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    @Override
    protected void onResume() {
        videoMain.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        videoMain.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoMain.stopPlayback();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
        LessonTitleActivity.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
            LessonTitleActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}