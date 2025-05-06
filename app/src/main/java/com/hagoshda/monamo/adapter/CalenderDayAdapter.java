package com.hagoshda.monamo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hagoshda.monamo.MainActivity;
import com.hagoshda.monamo.R;
import com.hagoshda.monamo.model.MemoList;
import com.hagoshda.monamo.viewModel.CalenderAdapterViewModel;
import com.hagoshda.monamo.viewModel.MemoListViewModel;

import org.w3c.dom.Text;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalenderDayAdapter extends RecyclerView.Adapter<CalenderDayAdapter.DateViewHolder>{

    private Context context;
    private CalenderAdapterViewModel calenderAdapterViewModel;
    private MemoListViewModel memoListViewModel;
    private MemoList memoList;

    private int day;
    private DayOfWeek week;
    private int month;
    private int year;

    private LocalDate localDate;

    public void setToday() {
        localDate = LocalDate.now();
        year = localDate.getYear();
        month = localDate.getMonthValue();
        week = localDate.getDayOfWeek();
        day = localDate.getDayOfMonth();

        ((MainActivity) context).setTextViewYearMonth(year, month);
        updateWeekendTexts();
    }

    public void setWeekP() {
        this.day = ++day;
        if (calenderAdapterViewModel.getLastDay(year, month) < day) {
            this.month = ++this.month;
            this.day = 1;
        }
        if (this.month > 12) {
            this.year = ++this.year;
            this.month = 1;
        }
        ((MainActivity) context).setTextViewYearMonth(year, month);
        updateWeekendTexts();
    }

    public void setWeekM() {
        this.day = --day;
        if (0 >= day) {
            this.month = --this.month;
            this.day = calenderAdapterViewModel.getLastDay(year, month);
        }
        if (this.month < 1) {
            this.year = --this.year;
            this.month = 12;
        }
        ((MainActivity) context).setTextViewYearMonth(year, month);
        updateWeekendTexts();
    }

    public CalenderDayAdapter(Calendar calendar, Context context, MemoList memoList) {
        this.context = context;
        memoListViewModel = new MemoListViewModel(context);

        localDate = LocalDate.now();
        year = localDate.getYear();
        month = localDate.getMonthValue();
        week = localDate.getDayOfWeek();
        day = localDate.getDayOfMonth();

        this.memoList = memoList;

        calenderAdapterViewModel = new CalenderAdapterViewModel(calendar, context, month);
    }

    @NonNull
    @Override
    public CalenderDayAdapter.DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_calender, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderDayAdapter.DateViewHolder holder, int position) {
        ((MainActivity) context).setTextViewYearMonth(year, month);
        ((MainActivity) context).setMemoTitle(memoList.getTitle());

        holder.day_tv.setText(week + " " + day);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView day_tv;
        TextView day_context_tv;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            day_tv = itemView.findViewById(R.id.day_tv);
            day_context_tv = itemView.findViewById(R.id.day_context_tv);
        }
    }

    public void updateWeekendTexts() {
        notifyDataSetChanged();
    }
}
