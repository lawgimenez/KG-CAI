package com.example.kg_cai.helpers;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.example.kg_cai.R;
import java.util.ArrayList;
import java.util.Random;

public class MyServiceMusic extends Service {
    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<Integer> songs = new ArrayList<>(); //arraylist consists of audios
        songs.add(0, R.raw.cute_bg_music_1);
        songs.add(1, R.raw.cute_bg_music);
        songs.add(2, R.raw.cute_bg_music_2);
        songs.add(3, R.raw.cute_bg_music_3);
        songs.add(4, R.raw.cute_bg_music_4);

        final int min = 0;
        final int max = 5;
        final int random = new Random().nextInt((max - min) + 1) + min; //set a random number to randomly select a index in arraylist that have songs

        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(random)); //play songs based on random number
        mediaPlayer.setLooping(false);
    }

    public void onStart(Intent intent, int startID){
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}