package com.example.kg_cai;

import static com.example.kg_cai.adapter.LessonTitleAdapter.lessonTitle_picked;
import static com.example.kg_cai.adapter.SubjectAdapter.subject_picked;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.example.kg_cai.helpers.LessonModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;
    private RecyclerView lessonRV;

    LessonAdapter lessonAdapter;
    List<LessonModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

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
}