package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetsActivity extends AppCompatActivity {

    private Toolbar setsToolbar;
    private GridView setsGridView;
    private FirebaseFirestore firestore;

    public static int category_id;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        setsToolbar = findViewById(R.id.setsToolbar);
        setsGridView = findViewById(R.id.setsGridView);

        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_bar); //initialize the loading dialog
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        category_id = getIntent().getIntExtra("SUBJ_ID",1); //it is from the subjGridAdapter


        String title = getIntent().getStringExtra("SUBJECT_PICKED");
        setSupportActionBar(setsToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance(); //initialization of firestore

        loadSets();

//        SetsAdapter adapter = new SetsAdapter("Quiz", 3); //pass to the constructor of setsAdapter
//
//        setsGridView.setAdapter(adapter);

    }

    private void loadSets() {
        firestore.collection("QUIZ").document("CAT" + String.valueOf(category_id))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists()){
                        long sets = (long) doc.get("SETS");//get the SETS of categories in firebase

                            SetsAdapter adapter = new SetsAdapter("Quiz", Integer.valueOf((int)sets)); //pass to the constructor of setsAdapter

                            setsGridView.setAdapter(adapter);

                    }else{
                        Toast.makeText(getApplicationContext(), "No Sets found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                loadingDialog.cancel();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}