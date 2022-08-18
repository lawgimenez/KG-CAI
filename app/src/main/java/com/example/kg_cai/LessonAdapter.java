package com.example.kg_cai;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kg_cai.helpers.LessonModel;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonHolder> {


    List<LessonModel> list;
    Context context;

    public LessonAdapter(List<LessonModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LessonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_item_layout,parent,false);
        return new LessonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonHolder holder, int position) {
        LessonModel lessonModel = list.get(position);

        if(lessonModel.getImg() != null){ //if it has image, the imgSound visibility will became visible and set the image of that text
            holder.imgLesson.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load((lessonModel.getImg())) // Uri of the picture
                    .into(holder.imgLesson);
        }


        holder.txtAddedLesson.setText(lessonModel.getText());

        holder.lessonCardView.setOnClickListener(new View.OnClickListener() { //if play button was clicked the audio will play
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(lessonModel.getAudio());
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
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class LessonHolder extends RecyclerView.ViewHolder{
        private ImageView imgLesson;
        private TextView txtAddedLesson;
        private CardView lessonCardView;

        public LessonHolder(@NonNull View itemView) {
            super(itemView);
            imgLesson = itemView.findViewById(R.id.imgLesson);
            txtAddedLesson = itemView.findViewById(R.id.txtAddedLesson);
            lessonCardView = itemView.findViewById(R.id.lessonCardView);
        }
    }
}
