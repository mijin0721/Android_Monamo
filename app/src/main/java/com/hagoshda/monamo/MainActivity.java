package com.hagoshda.monamo;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hagoshda.monamo.adapter.CalendarDayAdapter;
import com.hagoshda.monamo.adapter.CalenderWeekendAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView calendarDayRecyclerView;
    private RecyclerView calendarWeekendRecyclerView;
    private TextView textMonth;
    private Calendar calendar;
    private CalendarDayAdapter calendarAdapter;
    private CalenderWeekendAdapter calenderWeekendAdapter;
    private TextView planMemo;
    private TextView carryMemo;
    private TextView dayPlanMemo;

    private ArrayList<String> dayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initCalender();
        initWeekendCalenderView();
        initDayCalenderView();
        totalView();
    }

    public void totalView() {
        planMemo.setText("📝: " + calendarAdapter.getPlanMemo());
        carryMemo.setText("💸: " + calendarAdapter.getCarryMemo());
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        dayPlanMemo.setText("📝to" + day +": " + calendarAdapter.getPlanMemoDay(day));
    }

    private void initViews() {
        textMonth = findViewById(R.id.text_month);
        calendarDayRecyclerView = findViewById(R.id.calendarDayRecyclerView);
        calendarWeekendRecyclerView = findViewById(R.id.calendarWeekendRecyclerView);
        planMemo = findViewById(R.id.planMemo);
        carryMemo = findViewById(R.id.carryMemo);
        dayPlanMemo = findViewById(R.id.DayPlanMemo);
    }

    private void initDayCalenderView() {
        calendarDayRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        calendarAdapter = new CalendarDayAdapter(dayList, calendar, this);
        calendarDayRecyclerView.setAdapter(calendarAdapter);
        calendarDayRecyclerView.suppressLayout(true);
    }

    private void initWeekendCalenderView() {
        calendarWeekendRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        calenderWeekendAdapter = new CalenderWeekendAdapter(this);
        calendarWeekendRecyclerView.setAdapter(calenderWeekendAdapter);
        calendarWeekendRecyclerView.suppressLayout(true);
    }

    private void initCalender() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 공백 추가 (시작 요일 맞추기)
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < firstDayOfWeek; i++) {
            dayList.add("");
        }

        for (int i = 1; i <= daysInMonth; i++) {
            dayList.add(String.valueOf(i));
        }
    }
}