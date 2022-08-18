package com.example.kg_cai;

import static com.example.kg_cai.SplashActivity.catList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kg_cai.adapter.SubjGridAdapter;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            SubjectActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}