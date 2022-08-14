package com.example.kg_cai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.kg_cai.R;
import com.example.kg_cai.helpers.TextRecogModel;


import java.util.List;

public class TextRecogAdapter extends RecyclerView.Adapter<TextRecogAdapter.TextRecogViewAdapter> {

    List<TextRecogModel> list;
    Context context;
    public static String correctAns;
    public TextRecogAdapter(List<TextRecogModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TextRecogViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.textrecog_item_layout,parent,false);
        return new TextRecogViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextRecogViewAdapter holder, int position) {
        TextRecogModel textRecogModel = list.get(position);

        Glide.with(context)
                .load((textRecogModel.getImg())) // Uri of the picture
            .into(holder.imgInstruction);

        holder.txtInstruction.setText("Instructions: " + textRecogModel.getInstructions());
        holder.txtCorrectAns.setText(textRecogModel.getCorrectAns());

        correctAns = textRecogModel.getCorrectAns();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TextRecogViewAdapter extends RecyclerView.ViewHolder {
        ImageView imgInstruction;
        TextView txtInstruction,txtCorrectAns;

        public TextRecogViewAdapter(@NonNull View itemView) {
            super(itemView);
            imgInstruction = itemView.findViewById(R.id.imgInstruction);
            txtInstruction = itemView.findViewById(R.id.txtInstructions);
            txtCorrectAns = itemView.findViewById(R.id.txtCorrectAns);
        }
    }
}
