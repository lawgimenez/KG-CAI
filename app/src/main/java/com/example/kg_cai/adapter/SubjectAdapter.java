package com.example.kg_cai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kg_cai.LessonTitleActivity;
import com.example.kg_cai.R;
import com.example.kg_cai.helpers.SubjectModel;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.HolderSubject>{
    public static String subject_picked;

    FirebaseStorage firebaseStorage; //used to uploading audio files
    List<SubjectModel> list;
    Context context;

    public SubjectAdapter(List<SubjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderSubject onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_item_layout,parent,false);
        return new HolderSubject(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSubject holder, int position) {
        SubjectModel subjectModel = list.get(position);

        Glide.with(context)
                .load((subjectModel.getSubjImg())) // Uri of the picture
                .into(holder.imgSubj);

        holder.txtSubjName.setText(subjectModel.getTitle()); //set the title

        holder.cardViewSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject_picked = (subjectModel.getTitle());
                context.startActivity(new Intent(context.getApplicationContext(), LessonTitleActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HolderSubject extends RecyclerView.ViewHolder {
            ImageView imgSubj;
            TextView txtSubjName;
            CardView cardViewSubject;

            public HolderSubject(@NonNull View itemView) {
            super(itemView);

            txtSubjName= itemView.findViewById(R.id.txtSubjName);
            imgSubj = itemView.findViewById(R.id.imgSubj);
            cardViewSubject = itemView.findViewById(R.id.cardViewSubject);
        }
    }
}
