package com.hagoshda.monamo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hagoshda.monamo.MainActivity;
import com.hagoshda.monamo.model.MemoList;
import com.hagoshda.monamo.R;
import com.hagoshda.monamo.viewModel.CalenderAdapterViewModel;
import com.hagoshda.monamo.viewModel.MemoListViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.DateViewHolder>{

    private Context context;
    private CalenderAdapterViewModel calenderAdapterViewModel;
    private MemoListViewModel memoListViewModel;
    private MemoList memoList;

    private int month;
    private int year;

    private LocalDate localDate;

    private ArrayList<String> dayList = new ArrayList<>();

    public CalenderAdapter(Calendar calendar, Context context, MemoList memoList) {
        this.context = context;
        memoListViewModel = new MemoListViewModel(context);

        localDate = LocalDate.now();
        year = localDate.getYear();
        month = localDate.getMonthValue();

        this.memoList = memoList;

        calenderAdapterViewModel = new CalenderAdapterViewModel(calendar, context, month);
    }

    public void setMemoList(int i) {
        this.month = localDate.getMonthValue();
        this.year = localDate.getYear();

        this.memoList = memoListViewModel.getMemoList(i);
        updateWeekendTexts();
    }

    public void setToday() {
        this.month = localDate.getMonthValue();
        this.year = localDate.getYear();

        ((MainActivity) context).setTextViewYearMonth(year, month);
        updateWeekendTexts();
    }

    public void setMonthP() {
        this.month = ++this.month;
        if (this.month > 12) {
            this.year = ++this.year;
            this.month = 1;
        }
        ((MainActivity) context).setTextViewYearMonth(year, month);
        updateWeekendTexts();
    }

    public void setMonthM() {
        this.month = --this.month;
        if (this.month < 1) {
            this.year = --this.year;
            this.month = 12;
        }
        ((MainActivity) context).setTextViewYearMonth(year, month);
        updateWeekendTexts();
    }

    @NonNull
    @Override
    public CalenderAdapter.DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calender, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderAdapter.DateViewHolder holder, int position) {
        ((MainActivity) context).setTextViewYearMonth(year, month);
        ((MainActivity) context).setMemoTitle(memoList.getTitle());

        for (int i = 0; i < holder.weekendImages.length; i++) { //todo item_calender에서 imageview가 weekend_tv 보다 아래로 내리기
            holder.weekendImages[i].setVisibility(View.GONE);
        }

        for (int i = 0; i < holder.weekendTextList.size(); i++) { //todo textviewlist 다 검정색으로 바꾸기
            for (int j = 0; j < holder.weekendTextList.get(i).length; j++) {
                holder.weekendTextList.get(i)[j].setTextColor(Color.BLACK);
            }
        }
        
        if (calenderAdapterViewModel.checkMonth(month)) {
            dayList = calenderAdapterViewModel.initCalender(year, month);
            initCalenderDayView(holder);
//            clickShowMemo(holder);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        LinearLayout calender_LL;
        TextView calender_review_tv;
        LinearLayout weekend_1;
        LinearLayout weekend_2;
        LinearLayout weekend_3;
        LinearLayout weekend_4;
        LinearLayout weekend_5;
        LinearLayout weekend_6;

        TextView[] weekendTexts = new TextView[42];
        LinearLayout[] weekendLL = new LinearLayout[42];
        ArrayList<TextView[]> weekendTextList = new ArrayList<TextView[]>(42);
        ImageView[] weekendImages = new ImageView[42];

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            calender_LL = itemView.findViewById(R.id.calender_LL);
            calender_review_tv = itemView.findViewById(R.id.calender_review_tv);
            weekend_1 = itemView.findViewById(R.id.weekend_1);
            weekend_2 = itemView.findViewById(R.id.weekend_2);
            weekend_3 = itemView.findViewById(R.id.weekend_3);
            weekend_4 = itemView.findViewById(R.id.weekend_4);
            weekend_5 = itemView.findViewById(R.id.weekend_5);
            weekend_6 = itemView.findViewById(R.id.weekend_6);

            String[] days = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

            for (int week = 1; week <= 6; week++) {
                for (int day = 0; day < 7; day++) {
                    String resName = "weekend_" + week + "_" + days[day] + "_LL";
                    int resId = itemView.getResources().getIdentifier(resName, "id", itemView.getContext().getPackageName());
                    weekendLL[(week - 1) * 7 + day] = itemView.findViewById(resId);
                }
            }

            for (int week = 1; week <= 6; week++) {
                for (int day = 0; day < 7; day++) {
                    String resName = "weekend_" + week + "_" + days[day] + "_tv";
                    int resId = itemView.getResources().getIdentifier(resName, "id", itemView.getContext().getPackageName());
                    weekendTexts[(week - 1) * 7 + day] = itemView.findViewById(resId);
                }
            }

            for (int week = 1; week <= 6; week++) {
                for (int day = 0; day < 7; day++) {
                    TextView[] textViews = new TextView[6];
                    for (int i = 0; i < 6; i++) {
                        int j = i+1;
                        String resName = "weekend_" + week + "_" + days[day] + "_list" + j + "_tv";
                        int resId = itemView.getResources().getIdentifier(resName, "id", itemView.getContext().getPackageName());
                        textViews[i] = itemView.findViewById(resId);
                    }
                    int j = (week - 1) * 7 + day;
                    weekendTextList.add(j, textViews);
                }
            }

            for (int week = 1; week <= 6; week++) {
                for (int day = 0; day < 7; day++) {
                    String resName = "weekend_" + week + "_" + days[day] + "_image";
                    int resId = itemView.getResources().getIdentifier(resName, "id", itemView.getContext().getPackageName());
                    weekendImages[(week - 1) * 7 + day] = itemView.findViewById(resId);
                }
            }
        }
    }

    public void updateWeekendTexts() {
        notifyDataSetChanged();
    }

    private void initCalenderDayView(CalenderAdapter.DateViewHolder holder) {
        int weekPer = calenderAdapterViewModel.getWeekendPerMonth(year, month);

        if (weekPer == 5) {
            holder.weekend_6.setVisibility(View.GONE);
        } else if (weekPer == 6) {
            holder.weekend_6.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < 42 ; i++) {
            if (dayList.get(i).equals("0")) {
                holder.weekendTexts[i].setText("\t");
            } else {
                holder.weekendTexts[i].setText(dayList.get(i));
            }

//            String today = MoneyMemo.formatDate(year, month-1, Integer.parseInt(dayList.get(i)));
//            MoneyMemo money = moneyViewModel.getMemo(today);
//            setTextViewMoneyList(holder, i, money);
        }

//        setReviewTextView(holder, year, month-1, localDate.getDayOfMonth());
    }

    /*
    private void setReviewTextView(CalenderAdapter.DateViewHolder holder, int year, int month, int day) {
        String plan =  "📝: " +  moneyViewModel.getMonthPlan(year, month);
        String planToday = "📝to" + day + ": " + moneyViewModel.getMonthPlanToToday(year, month, day);
        String carry = "💸: " + moneyViewModel.getMonthCarry(year, month);

        if (month == localDate.getMonthValue()-1) {
            holder.calender_review_tv.setText(plan + "    " + planToday + "    " + carry);
        } else {
            holder.calender_review_tv.setText(plan + "    " + carry);
        }
    }

    private void clickShowMemo(CalenderAdapter.DateViewHolder holder) {
        for (int i = 0; i < dayList.size(); i++) {
            final int index = i;
            if (!Objects.equals(dayList.get(i), "0")) {
                int finalI = i;
                holder.weekendLL[i].setOnClickListener(v -> {
                    String key = MoneyMemo.formatDate(year, month-1, Integer.parseInt(dayList.get(index)));
                    showMemoDialog(key, () -> {
                        MoneyMemo money = moneyViewModel.getMemo(key);
                        setTextViewMoneyList(holder, finalI, money);
                        setReviewTextView(holder, year,month-1, localDate.getDayOfMonth());
                    });
                });
            }
        }
    }

    private void setTextViewMoneyList(CalenderAdapter.DateViewHolder holder, int count, MoneyMemo money) {
        if (money.getPlanMoney() != 0) {
            holder.weekendTextList.get(count)[0].setText("📝: " + money.getPlanMoney());
        } else {
            holder.weekendTextList.get(count)[0].setText("\t");
        }

        if (money.getCarryMoney() != 0) {
            holder.weekendTextList.get(count)[1].setText("💸: " + money.getCarryMoney());
        } else {
            holder.weekendTextList.get(count)[1].setText("\t");
        }
    }

    private void showMemoDialog(String dateKey, OnMemoSavedListener listener) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dateKey + " 추가");

        layout.setPadding(50, 40, 50, 10);
        final EditText plan = new EditText(context);
        final EditText carry = new EditText(context);

        if (moneyViewModel.getMemo(dateKey).getPlanMoney() != 0) {
            plan.setText(String.valueOf(moneyViewModel.getMemo(dateKey).getPlanMoney()));
        }

        if (moneyViewModel.getMemo(dateKey).getCarryMoney() != 0) {
            carry.setText(String.valueOf(moneyViewModel.getMemo(dateKey).getCarryMoney()));
        }

        layout.addView(plan);
        layout.addView(carry);

        builder.setView(layout);

        builder.setPositiveButton("저장", (dialog, which) -> {
            String planMemo = plan.getText().toString();
            if (planMemo.isEmpty()) {
                planMemo = "0";
            }
            String carryMemo = carry.getText().toString();
            if (carryMemo.isEmpty()) {
                carryMemo = "0";
            }
            MoneyMemo moneyMemo = new MoneyMemo(dateKey, Integer.parseInt(planMemo), Integer.parseInt(carryMemo));
            moneyViewModel.saveMemo(moneyMemo);
            listener.onMemoSaved();
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }

     */
}
