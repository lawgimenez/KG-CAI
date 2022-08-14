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
import com.example.kg_cai.helpers.TextModel;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewAdapter> {

    List<TextModel> list;
    Context context;

    public TextAdapter(List<TextModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TextViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.text_item_layout,parent,false);
        return new TextViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewAdapter holder, int position) {
        TextModel textModel = list.get(position);

        Glide.with(context)
                .load((textModel.getImg())) // Uri of the picture
            .into(holder.imgText);

        holder.txtTitle.setText("Title: " + textModel.getTitle());
        holder.txtText.setText(textModel.getText());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TextViewAdapter extends RecyclerView.ViewHolder {
        ImageView imgText;
        TextView txtTitle,txtText;

        public TextViewAdapter(@NonNull View itemView) {
            super(itemView);
            imgText = itemView.findViewById(R.id.imgText);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtText = itemView.findViewById(R.id.txtText);
        }
    }
}
