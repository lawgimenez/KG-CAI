package com.example.kg_cai.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kg_cai.R;
import com.example.kg_cai.helpers.SoundModel;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundHolder> {

    List<SoundModel> list;
    Context context;

    public SoundAdapter(List<SoundModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sets_sounds, parent,false);
        return new SoundHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
        SoundModel soundModel = list.get(position);

        holder.txtSounds.setText(soundModel.getText());

        holder.soundsLinearLayout.setOnClickListener(new View.OnClickListener() { //if item was clicked the audio will play
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setLooping(false);
                try{
                    mediaPlayer.setDataSource(soundModel.getAudio());

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    mediaPlayer.prepare();
                }catch (Exception e){
                    Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(soundModel.getImg() != null){ //If it has a picture, the imgSound pic will became visible
            holder.imgSound.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load((soundModel.getImg())) // Uri of the picture
                    .into(holder.imgSound);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SoundHolder extends RecyclerView.ViewHolder{
        private TextView txtSounds;
        private ImageView imgSound;
        private LinearLayout soundsLinearLayout;

        public SoundHolder(@NonNull View itemView) {
            super(itemView);

            imgSound = itemView.findViewById(R.id.imgSound);
            txtSounds = itemView.findViewById(R.id.txtSounds);
            soundsLinearLayout = itemView.findViewById(R.id.soundsLinearLayout);
        }
    }

}
