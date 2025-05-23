package com.hagoshda.monamo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hagoshda.monamo.MainActivity;
import com.hagoshda.monamo.R;
import com.hagoshda.monamo.model.MemoList;
import com.hagoshda.monamo.model.MoneyMemo;
import com.hagoshda.monamo.viewModel.CalenderAdapterViewModel;
import com.hagoshda.monamo.viewModel.MemoListViewModel;
import com.hagoshda.monamo.viewModel.MoneyViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CalenderWeekAdapter extends RecyclerView.Adapter<CalenderWeekAdapter.DateViewHolder>{

    private Context context;
    private CalenderAdapterViewModel calenderAdapterViewModel;
    private MemoListViewModel memoListViewModel;
    private MoneyViewModel moneyViewModel;
    private MemoList memoList;

    private int month;
    private int year;
    private int week;
    private int day;

    private LocalDate localDate;

    private ArrayList<String> dayList = new ArrayList<>();

    public void setToday() {
        localDate = LocalDate.now();
        year = localDate.getYear();
        month = localDate.getMonthValue();
        day = localDate.getDayOfMonth();

        int rows = 7;
        int index = dayList.indexOf(String.valueOf(day));
        week = (index / rows);

        ((MainActivity) context).setTextViewYearMonthWeek(year, month, week);
        updateWeekendTexts();
    }

    public void setWeekP() {
        this.week = ++week;
        if (calenderAdapterViewModel.getWeekendPerMonth(year, month)-1 <= week) {
            this.month = ++this.month;
            this.week = 0;
        }
        if (this.month > 12) {
            this.year = ++this.year;
            this.month = 1;
        }
        ((MainActivity) context).setTextViewYearMonthWeek(year, month, week);
        updateWeekendTexts();
    }

    public void setWeekM() {
        this.week = --week;
        if (1 > week) {
            this.month = --this.month;
            this.week = calenderAdapterViewModel.getWeekendPerMonth(year, month) - 1;
        }
        if (this.month < 1) {
            this.year = --this.year;
            this.month = 12;
        }
        ((MainActivity) context).setTextViewYearMonthWeek(year, month, week);
        updateWeekendTexts();
    }

    public CalenderWeekAdapter(Calendar calendar, Context context, MemoList memoList) {
        this.context = context;
        memoListViewModel = new MemoListViewModel(context);

        localDate = LocalDate.now();
        year = localDate.getYear();
        month = localDate.getMonthValue();
        day = localDate.getDayOfMonth();

        this.memoList = memoList;

        calenderAdapterViewModel = new CalenderAdapterViewModel(calendar, context, month);
    }

    @NonNull
    @Override
    public CalenderWeekAdapter.DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_week_calender, parent, false);
        return new CalenderWeekAdapter.DateViewHolder(view);
    }

    private boolean check = false;

    @Override
    public void onBindViewHolder(@NonNull CalenderWeekAdapter.DateViewHolder holder, int position) {
        dayList = calenderAdapterViewModel.generateMonthCalendar(year, month);
        ((MainActivity) context).setTextViewYearMonthWeek(year, month, week);
        ((MainActivity) context).setMemoTitle(memoList.getTitle());

        if (!check) {
            int rows = 7;
            int index = dayList.indexOf(String.valueOf(day));
            week = index / rows;
            check = true;
        }

        initCalenderDayView(holder);

        if (memoList.getMemoType() == MemoList.MemoType.EXPENSE_TRACKER) {
            moneyViewModel = new MoneyViewModel(context, memoList.getDbName());
            initContext(holder);
            clickShowMemo(holder);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    private void initCalenderDayView(CalenderWeekAdapter.DateViewHolder holder) {
        String[] theWeeks = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
        for (int i = 0; i < 7; i++) {
            holder.days[i].setText(theWeeks[i] + " " + dayList.get(week*7 + i));
        }
    }

    private void initContext(CalenderWeekAdapter.DateViewHolder holder) {
        for (int i = 0; i < 7; i++) {
            if (memoList.getMemoType() == MemoList.MemoType.EXPENSE_TRACKER) {
                MoneyMemo moneyMemo = moneyViewModel.getMemo(MoneyMemo.formatDate(year, month, Integer.parseInt(dayList.get(week*7 + i))));
                setTextViewMoneyList(holder, i, moneyMemo);
            }
        }
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        LinearLayout day_1;
        LinearLayout day_2;
        LinearLayout day_3;
        LinearLayout day_4;
        TextView[] days = new TextView[7];
        TextView[] contexts = new TextView[8];

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            day_1 = itemView.findViewById(R.id.day_1);
            day_2 = itemView.findViewById(R.id.day_2);
            day_3 = itemView.findViewById(R.id.day_3);
            day_4 = itemView.findViewById(R.id.day_4);

            for (int i = 0; i < 7; i++) {
                int week = i / 2 + 1;
                int day = i % 2 + 1;
                String idName = "day_" + week + "_" + day + "_tv";
                int resId = itemView.getResources().getIdentifier(idName, "id", itemView.getContext().getPackageName());
                days[i] = itemView.findViewById(resId);
            }

            for (int i = 0; i < 8; i++) {
                int week = i / 2 + 1;
                int day = i % 2 + 1;
                String idName = "day_" + week + "_" + day + "_context_tv";
                int resId = itemView.getResources().getIdentifier(idName, "id", itemView.getContext().getPackageName());
                contexts[i] = itemView.findViewById(resId);
            }

        }
    }

    public void updateWeekendTexts() {
        notifyDataSetChanged();
    }

    private void setTextViewMoneyList(DateViewHolder holder, int count, MoneyMemo money) {
        String text = "";
        if (money.getPlanMoney() != 0) {
            text = "📝: " + money.getPlanMoney();
        } else {
            text = "\t";
        }

        if (money.getCarryMoney() != 0) {
            text += "\n💸: " + money.getCarryMoney();
        } else {
            text += "\t";
        }

        holder.contexts[count].setText(text);
    }

    private void clickShowMemo(CalenderWeekAdapter.DateViewHolder holder) {
        for (int i = 0; i < 7; i++) {
            final int index = i;
            if (!Objects.equals(dayList.get(i), "0")) {
                int finalI = i;
                holder.contexts[i].setOnClickListener(v -> {
                    if (memoList.getMemoType() == MemoList.MemoType.EXPENSE_TRACKER) {
                        String key = MoneyMemo.formatDate(year, month - 1, Integer.parseInt(dayList.get(week*7 + index)));
                        moneyViewModel.showMemoDialog(key, () -> {
                            MoneyMemo money = moneyViewModel.getMemo(key);
                            setTextViewMoneyList(holder, finalI, money);
//                        setReviewTextView(holder, year,month-1, localDate.getDayOfMonth());
                        });
                    }
                });
            }
        }
    }
}
