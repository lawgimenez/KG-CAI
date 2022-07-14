package com.example.kg_cai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kg_cai.R;
import com.example.kg_cai.helpers.RecyclerViewAction;
import com.example.kg_cai.helpers.ShapeHelper;

import java.util.ArrayList;

//import ideanity.oceans.kidslearning.R;
//import ideanity.oceans.kidslearning.RecyclerViewAction;
//import ideanity.oceans.kidslearning.helpers.ShapeHelper;

public class ShapeAdapter extends RecyclerView.Adapter<ShapeAdapter.ColorViewHolder>{
    ArrayList<ShapeHelper> shape;
    private Context mContext;
    private RecyclerViewAction recyclerViewAction;

    public ShapeAdapter(ArrayList<ShapeHelper> shape, Context mContext, RecyclerViewAction recyclerViewAction) {
        this.shape = shape;
        this.mContext = mContext;
        this.recyclerViewAction = recyclerViewAction;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allshapes, parent, false);
        ColorViewHolder colorViewHolder = new ColorViewHolder(view);
        return colorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        final ShapeHelper shapeHelper = shape.get(position);

        holder.title.setImageResource(shapeHelper.getImage());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAction.onViewClicked(v.getId(), holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return shape.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {

        ImageView title;
        public View layout;
        CardView container;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            //Hooks
            title = itemView.findViewById(R.id.content);

        }


    }


}























