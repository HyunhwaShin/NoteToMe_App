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

    public DiaryListAdapter(Context context, ArrayList<String> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    private ArrayList<String> dataList;
    private Context mContext;
    private RecyclerView rv;

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.diaryText.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener
    { void onItemClick(View v, int position);    }

    // OnItemClickListener 객체 참조를 어댑터에 전달
    public void setOnItemClickListener(OnItemClickListener listener)
    { this.mListener = listener; }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView diaryText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diaryText = itemView.findViewById(android.R.id.text1);

            //클릭시
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {// 클릭시 이벤트

                        Intent intent = new Intent(mContext, SelectDiaryActivity.class);
                        mContext.startActivity(intent);

                    }
                }
            });
        }
    }


}

