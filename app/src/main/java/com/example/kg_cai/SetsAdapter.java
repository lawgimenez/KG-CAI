package com.example.kg_cai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SetsAdapter extends BaseAdapter {

    private String setsName;
    private int numOfSets;

    public SetsAdapter(String setsName, int numOfSets) {
        this.setsName = setsName;
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

        ((TextView) view.findViewById(R.id.txtSetName)).setText("Quiz ....");
        ((TextView) view.findViewById(R.id.txtSetNumber)).setText(String.valueOf(position+1));


        return view;
    }
}
