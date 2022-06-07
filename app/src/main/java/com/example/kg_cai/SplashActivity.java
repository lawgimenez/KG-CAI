package com.example.kg_cai;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgLogo;

    public static List<String> catList = new ArrayList<>(); //this is the arraylist of the subjects from firebase
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo = findViewById(R.id.imgLogo);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.logoanim);

        imgLogo.setAnimation(anim);

        firestore = FirebaseFirestore.getInstance(); //initialization for firestore

        new Thread(new Runnable() {
            @Override
            public void run() {
//                try{
//                    sleep(1000); //sleep 1 second to load data and open login activity
                    loadData();

//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }

            }
        }).start();
    }

    private void loadData() {
        catList.clear(); //Clear the arraylist of the subject

        firestore.collection("QUIZ").document("Categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        long count = (long)doc.get("COUNT"); //get the COUNT of categories in firebase

                        for(int i = 1; i<=count; i++){
                            String catName = doc.getString("CAT"+String.valueOf(i)); //getting the CAT and loop it to get the CAT in firebase

                            catList.add(catName); //add it to the catList

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class)); //start the loginActivity after fetching subjects data
                            finish();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No Subject found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}