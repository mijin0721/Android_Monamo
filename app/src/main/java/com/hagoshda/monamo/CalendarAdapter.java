package com.hagoshda.monamo;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hagoshda.monamo.data.MemoStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DateViewHolder> {

    private ArrayList<String> dayList;
    private Calendar calendar;
    private Context context;

    private MemoStorage memoStorage;

    public CalendarAdapter(ArrayList<String> dayList, Calendar calendar, Context context) {
        this.dayList = dayList;
        this.calendar = calendar;
        this.context = context;
        memoStorage = new MemoStorage(context);
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String day = dayList.get(position);
        holder.textDate.setText(day);

        if (!day.equals("")) {
            holder.dayLL.setOnClickListener(v -> {
                String key = memoStorage.formatDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(day));
                showMemoDialog(key);
            });
        }

        if (!day.isEmpty()) {
            holder.plan_date.setText(memoStorage.getPlanDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(day)));
            holder.carry_date.setText(memoStorage.getCarryDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(day)));
        }
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        LinearLayout dayLL;
        TextView textDate;
        TextView plan_date;
        TextView carry_date;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dayLL = itemView.findViewById(R.id.dateLL);
            textDate = itemView.findViewById(R.id.text_date);
            plan_date = itemView.findViewById(R.id.plan_date);
            carry_date = itemView.findViewById(R.id.carry_date);
        }
    }

    public String getPlanMemo() {
        String planTotal = memoStorage.getPlanTotal(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        Log.d("", planTotal);
        if (planTotal.equals("")) {
            return "없음";
        }
        return planTotal;
    }

    public String getCarryMemo() {
        String planTotal = memoStorage.getCarryTotal(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        Log.d("", planTotal);
        if (planTotal.equals("")) {
            return "없음";
        }
        return planTotal;
    }

    private void showMemoDialog(String dateKey) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dateKey + " 메모");

        final EditText input = new EditText(context);
        input.setText(memoStorage.getMemo(dateKey));
        builder.setView(input);

        builder.setPositiveButton("저장", (dialog, which) -> {
            memoStorage.saveMemo(dateKey, input.getText().toString());
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }
}
