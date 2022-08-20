package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.kg_cai.adapter.SubjectAdapter;
import com.example.kg_cai.helpers.SubjectModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {

    private RecyclerView subjRecyclerView;
    private VideoView videoMain;

    FirebaseDatabase firebaseDatabase; //use to store URLs of uploaded files
    DatabaseReference mDBReference;
    FirebaseStorage firebaseStorage; //used to uploading audio files
    StorageReference mStorage;

    SubjectAdapter subjectAdapter;
    List<SubjectModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        playBackgroundVideo();

        subjRecyclerView = findViewById(R.id.subjRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        subjRecyclerView.setLayoutManager(manager);
        subjRecyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDBReference = firebaseDatabase.getReference().child("Subjects");
        mStorage = FirebaseStorage.getInstance().getReference(); //Return an object of firebase storage

        Toolbar toolbar = findViewById(R.id.toolbar_subj);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Subjects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDBReference.orderByChild("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SubjectModel subjectModel = dataSnapshot.getValue(SubjectModel.class);
                    list.add(subjectModel);
                }
                subjectAdapter = new SubjectAdapter(list, SubjectActivity.this);
                subjRecyclerView.setAdapter(subjectAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playBackgroundVideo() {
        videoMain = findViewById(R.id.subjMain);
        String path = "android.resource://"+getPackageName()+"/"+R.raw.third_bg;
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
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            SubjectActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}