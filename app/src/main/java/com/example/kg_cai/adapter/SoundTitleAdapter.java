package com.example.kg_cai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kg_cai.R;
import com.example.kg_cai.SoundActivity;
import com.example.kg_cai.helpers.SoundTitleModel;

import java.util.List;

public class SoundTitleAdapter extends RecyclerView.Adapter<SoundTitleAdapter.HolderSounds>{
    public static String sound_picked;
    List<SoundTitleModel> list;
    Context context;

    public SoundTitleAdapter(List<SoundTitleModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderSounds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sets_add_sounds_title_layout,parent,false);
        return new HolderSounds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSounds holder, int position) {
        SoundTitleModel soundTitleModel = list.get(position);

        holder.tvSoundsName.setText(soundTitleModel.getTitle()); //get the sound name

        holder.tvSoundsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound_picked = (soundTitleModel.getTitle());
                context.startActivity(new Intent(context.getApplicationContext(), SoundActivity.class));
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolderSounds extends RecyclerView.ViewHolder {
        TextView tvSoundsName;

        public HolderSounds(@NonNull View itemView) {
            super(itemView);
            tvSoundsName = itemView.findViewById(R.id.tvSoundsName);
        }
    }
}
