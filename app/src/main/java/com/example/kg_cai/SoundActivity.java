package com.example.kg_cai;

import static com.example.kg_cai.adapter.SoundTitleAdapter.sound_picked;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kg_cai.adapter.SoundAdapter;
import com.example.kg_cai.helpers.MyServiceMusic;
import com.example.kg_cai.helpers.SoundModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SoundActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;
    private RecyclerView SoundsRv;

    SoundAdapter soundAdapter;
    List<SoundModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference().child("Sounds");

        SoundsRv = findViewById(R.id.SoundsRV);

        Toolbar toolbar = findViewById(R.id.toolbar_Sounds);
        setSupportActionBar(toolbar);
        toolbar.setTitle(sound_picked);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        SoundsRv.setLayoutManager(manager);
        SoundsRv.setHasFixedSize(true);

        list = new ArrayList<>();

        //service for background music use to stop
        new Intent(getApplicationContext(), MyServiceMusic.class);
        stopService(new Intent(getApplicationContext(), MyServiceMusic.class));

        mReference.child(sound_picked).orderByChild("Title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if (dataSnapshot.child("id").exists()){ //get the id because it is the only not string
                        SoundModel soundModel = dataSnapshot.getValue(SoundModel.class);
                        list.add(soundModel);
                    }
                }
                soundAdapter = new SoundAdapter(list, SoundActivity.this);
                SoundsRv.setAdapter(soundAdapter);
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
            SoundActivity.this.finish();
            startActivity(new Intent(getApplicationContext(), MainSoundActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}