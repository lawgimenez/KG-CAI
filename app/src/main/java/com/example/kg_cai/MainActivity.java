package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private Button btnStartQuiz, btnVideos, btnLeaderBoards, btnSounds;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainToolbar.inflateMenu(R.menu.main_menu);

        btnStartQuiz = findViewById(R.id.btnStartQuiz_Main);
        btnVideos = findViewById(R.id.btnVideos_Main);
        btnSounds = findViewById(R.id.btnSounds_Main);
        btnLeaderBoards = findViewById(R.id.btnLeaderboards);

        //click listener to logout
        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               if(item.getItemId() == R.id.itemLogout){
                   signOut();
               }
               else if(item.getItemId() == R.id.itemProfile){
                   profile();
               }
                return false;
            }
        });

        btnLeaderBoards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LeaderboardsActivity.class));
            }
        });

        btnSounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainSoundsActivity.class));
            }
        });


        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainVideosActivity.class));
            }
        });

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
            }
        });

    }

    private void profile() {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void signOut() { //method to log out the user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //this is for back button
        if (item.getItemId() == android.R.id.home) {
            MainActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}