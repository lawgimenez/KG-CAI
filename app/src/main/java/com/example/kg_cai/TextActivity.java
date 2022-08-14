package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.kg_cai.adapter.TextAdapter;
import com.example.kg_cai.helpers.TextModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import java.util.List;

public class TextActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mReference;
    FirebaseStorage firebaseStorage;

    private RecyclerView textRv;

    TextAdapter textAdapter;
    List<TextModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        textRv = findViewById(R.id.textRv);

        list = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        String sub = getIntent().getStringExtra("txtFolder"); //came from mainTextActivity
        mReference = firebaseDatabase.getReference().child(sub);

        Toolbar toolbar = findViewById(R.id.toolbar_texts);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mReference.orderByChild("Title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TextModel textModel = dataSnapshot.getValue(TextModel.class);
                    list.add(textModel);
                }
                textAdapter = new TextAdapter(list, TextActivity.this);
                textRv.setAdapter(textAdapter);

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
            TextActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}