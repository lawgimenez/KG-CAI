package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kg_cai.adapter.SoundAdapter;
import com.example.kg_cai.adapter.TextAdapter;
import com.example.kg_cai.helpers.SoundModel;
import com.example.kg_cai.helpers.TextModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class MainSoundActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;

    SoundAdapter soundAdapter;
    List<SoundModel> list;

    private RecyclerView soundRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sound);

        soundRv = findViewById(R.id.soundRv);
        list = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar_sound);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        soundRv.setLayoutManager(manager);
        soundRv.setHasFixedSize(true);
        list = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference().child("Sounds");

        mReference.orderByChild("Title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SoundModel soundModel = dataSnapshot.getValue(SoundModel.class);
                    list.add(soundModel);
                }
                soundAdapter = new SoundAdapter(list, MainSoundActivity.this);
                soundRv.setAdapter(soundAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            MainSoundActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        MainSoundActivity.this.finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}