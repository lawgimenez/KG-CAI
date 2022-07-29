package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kg_cai.databinding.ActivityMainBinding;
import com.example.kg_cai.helpers.MyServiceMusic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private Button btnStartQuiz, btnTextRecognition,btnLeaderBoards, btnText_Main, btnSound_Main,btnVideo_Main;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //btnTextRecognition = findViewById(R.id.btnTextRecognition);
        btnStartQuiz = findViewById(R.id.btnStartQuiz_Main);
        btnLeaderBoards = findViewById(R.id.btnLeaderboards);
        btnText_Main = findViewById(R.id.btnText_Main);
        btnSound_Main = findViewById(R.id.btnSounds_Main);
        btnVideo_Main = findViewById(R.id.btnVideos_Main);

        //service for background music use to start
        startService(new Intent(getApplicationContext(), MyServiceMusic.class));

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainToolbar.inflateMenu(R.menu.main_menu);


        //click listener to profile,settings and logout
        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               if(item.getItemId() == R.id.itemLogout){
                   firebaseAuth = FirebaseAuth.getInstance();
                   firebaseAuth.signOut();
                   startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                   finish();
               }
               else if(item.getItemId() == R.id.itemProfile){
                   startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
               }else if(item.getItemId() == R.id.itemSettings){
                   startActivity(new Intent(getApplicationContext(), AppSettingsActivity.class));
               }
                return false;
            }
        });

//
//        btnTextRecognition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), TextRecognitionActivity.class));
//            }
//        });

        btnLeaderBoards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LeaderboardsActivity.class));
            }
        });

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
            }
        });

        btnVideo_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainVideosActivity.class));
            }
        });

        btnSound_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainSoundsActivity.class));
            }
        });

        btnText_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainTextActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            MainActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}