package com.example.kg_cai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kg_cai.R;
import com.example.kg_cai.helpers.SoundModel;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.HolderSounds>{

    List<SoundModel> list;
    Context context;

    public SoundAdapter(List<SoundModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderSounds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sets_sounds_layout,parent,false);
        return new HolderSounds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSounds holder, int position) {
        SoundModel soundModel = list.get(position);

        holder.tvSoundsName.setText(soundModel.getTitle()); //get the sound name

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
