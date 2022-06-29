package com.example.kg_cai;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class SubjGridAdapter extends BaseAdapter {
    private List<SubjectModel> subjList;
    private Integer[] imageid;

    public SubjGridAdapter(List<SubjectModel> subjList, Integer[] imageid) {
        this.subjList = subjList;
        this.imageid = imageid;
    }

    @Override
    public int getCount() {
        return subjList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subj_item_layout, parent,false);
        }else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.selected_cat_index = position;
                Intent intent = new Intent(parent.getContext(),SetsActivity.class);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.subjName_itemLayout)).setText(subjList.get(position).getName());
        ((ImageView) view.findViewById(R.id.subjLogo_itemLayout)).setImageResource(imageid[position]);

//        Random rand = new Random();
//        int color = Color.argb(255,rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)); //gives you a random color for the background
//        view.setBackgroundColor(color);

        return view;
    }
}
