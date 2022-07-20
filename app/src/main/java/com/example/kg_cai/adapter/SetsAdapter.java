package com.example.kg_cai.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kg_cai.QuestionActivity;
import com.example.kg_cai.R;

public class SetsAdapter extends BaseAdapter {

    private String setsName;
    private int numOfSets;

    public SetsAdapter(String setsName, int numOfSets) {
        this.setsName = setsName;
        this.numOfSets = numOfSets;
    }

    public SetsAdapter(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    @Override
    public int getCount() {
        return numOfSets;
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
        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sets_item_layout,parent,false);
        }else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), QuestionActivity.class); //question activity will start after clicking one set
                intent.putExtra("SETNO", position);
                parent.getContext().startActivity(intent);
            }
        });


        ((TextView) view.findViewById(R.id.txtSetNumber)).setText(String.valueOf(position+1));


        return view;
    }
}
