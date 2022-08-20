package com.example.kg_cai;

import static com.example.kg_cai.adapter.LessonTitleAdapter.lessonTitle_picked;
import static com.example.kg_cai.adapter.SubjectAdapter.subject_picked;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.kg_cai.helpers.LessonModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {
    private VideoView videoMain;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;
    private RecyclerView lessonRV;

    LessonAdapter lessonAdapter;
    List<LessonModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        playBackgroundVideo();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference().child("Subjects").child(subject_picked).child("Lessons");

        Toolbar toolbar = findViewById(R.id.toolbar_Lesson);
        setSupportActionBar(toolbar);
        toolbar.setTitle(lessonTitle_picked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lessonRV = findViewById(R.id.lessonRV);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        lessonRV.setLayoutManager(manager);
        lessonRV.setHasFixedSize(true);
        list = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        lessonRV.setLayoutManager(mLayoutManager);

        mReference.child(lessonTitle_picked).orderByChild("Title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if (dataSnapshot.child("id").exists()){ //get the id because it is the only not string
                        LessonModel lessonModel = dataSnapshot.getValue(LessonModel.class);
                        list.add(lessonModel);
                    }
                }
                lessonAdapter = new LessonAdapter(list, LessonActivity.this);
                lessonRV.setAdapter(lessonAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playBackgroundVideo() {
        videoMain = findViewById(R.id.lessonVideoMain);
        String path = "android.resource://"+getPackageName()+"/"+R.raw.circle_bg;
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
        startActivity(new Intent(getApplicationContext(), LessonTitleActivity.class));
        LessonActivity.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), LessonTitleActivity.class));
            LessonActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}