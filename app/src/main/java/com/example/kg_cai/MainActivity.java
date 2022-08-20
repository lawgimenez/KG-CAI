package com.example.kg_cai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.kg_cai.helpers.MyServiceMusic;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private Button btnStartQuiz,btnLeaderBoards,btnVideo_Main, btnLesson_Main;
    private FirebaseAuth firebaseAuth;

    private AdView mAdView; //ads
    private VideoView videoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBackgroundVideo();
        showAds();

        //btnTextRecognition = findViewById(R.id.btnTextRecognition); ready for panel request
        btnStartQuiz = findViewById(R.id.btnStartQuiz_Main);
        btnLeaderBoards = findViewById(R.id.btnLeaderboards);
        btnVideo_Main = findViewById(R.id.btnVideos_Main);
        btnLesson_Main = findViewById(R.id.btnLesson_Main);

        //service for background music use to start
        //startService(new Intent(getApplicationContext(), MyServiceMusic.class));

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

// ready for panel request
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

        btnLesson_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
            }
        });

    }

    private void showAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() { //for ads
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void playBackgroundVideo() {
        videoMain = findViewById(R.id.videoMain);
        String path = "android.resource://"+getPackageName()+"/"+R.raw.main_bg;
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