package com.example.notetome2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder>  {

    public DiaryListAdapter(Context context, ArrayList<Diary> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    private ArrayList<Diary> dataList;
    private Context mContext;
    private RecyclerView rv;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.diaryText.setText(dataList.get(position).diary);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectDiaryActivity.class)
                        .putExtra("diary",dataList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView diaryText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            diaryText = itemView.findViewById(android.R.id.text1);

        }
    }

}

