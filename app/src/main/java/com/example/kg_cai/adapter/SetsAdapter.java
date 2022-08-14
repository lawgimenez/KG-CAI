package com.example.kg_cai.adapter;

import static com.example.kg_cai.SetsActivity.setsList;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kg_cai.QuestionActivity;
import com.example.kg_cai.R;
import com.example.kg_cai.helpers.SetsModelClass;
import java.util.List;

public class SetsAdapter extends RecyclerView.Adapter<SetsAdapter.ViewHolder> {
    public static String setTitle;
    private final List<SetsModelClass> sets_list;

    public SetsAdapter(List<SetsModelClass> sets_list) {

        this.sets_list = sets_list;
    }

    @NonNull
    @Override
    public SetsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sets_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetsAdapter.ViewHolder holder, int position) {
        setTitle = sets_list.get(position).getName();
        holder.setData(setTitle,position, setTitle,this);
    }

    @Override
    public int getItemCount() {
        return setsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView setName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            setName = itemView.findViewById(R.id.tvSetsName);

        }

        private void setData(String title, final int pos, final String setID, final SetsAdapter adapter)
        {
            setName.setText(title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(itemView.getContext(), QuestionActivity.class); //question activity will start after clicking one set
                    intent.putExtra("setNo", pos);
                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }
}