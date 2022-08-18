package com.example.kg_cai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kg_cai.LessonActivity;
import com.example.kg_cai.R;
import com.example.kg_cai.helpers.LessonTitleModel;

import java.util.List;

public class LessonTitleAdapter extends RecyclerView.Adapter<LessonTitleAdapter.HolderLessonTitle>{
    public static String lessonTitle_picked;
    List<LessonTitleModel> list;
    Context context;

    public LessonTitleAdapter(List<LessonTitleModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderLessonTitle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_title_layout,parent,false);
        return new HolderLessonTitle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderLessonTitle holder, int position) {
        LessonTitleModel lessonTitleModel = list.get(position);

        holder.txtLessonName.setText(lessonTitleModel.getTitle()); //get the sound name

        holder.txtLessonName.setOnClickListener(new View.OnClickListener() { //if play button was clicked it will play the sound
            @Override
            public void onClick(View v) {
                lessonTitle_picked = (lessonTitleModel.getTitle());
                context.startActivity(new Intent(context.getApplicationContext(), LessonActivity.class));
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HolderLessonTitle extends RecyclerView.ViewHolder {
        TextView txtLessonName;

        public HolderLessonTitle(@NonNull View itemView) {
            super(itemView);
            txtLessonName = itemView.findViewById(R.id.txtLessonName);
        }
    }
}
