package com.hagoshda.monamo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hagoshda.monamo.R;

public class CalenderWeekendAdapter extends RecyclerView.Adapter<CalenderWeekendAdapter.DateViewHolder>{

    private Context context;
    private String[] weekendList = new String[]{"일", "월", "화", "수", "목", "금", "토"};

    public CalenderWeekendAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weekend, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String weekend = weekendList[position];
        holder.textWeekend.setText(weekend);
    }

    @Override
    public int getItemCount() {
        return weekendList.length;
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView textWeekend;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            textWeekend = itemView.findViewById(R.id.textWeekend);
        }
    }
}
