package com.hagoshda.monamo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView calendarRecyclerView;
    private TextView textMonth;
    private Calendar calendar;
    private CalendarAdapter calendarAdapter;
    private TextView planMemo;
    private TextView carryMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMonth = findViewById(R.id.text_month);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);

        planMemo = findViewById(R.id.planMemo);
        carryMemo = findViewById(R.id.carryMemo);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        ArrayList<String> dayList = new ArrayList<>();

        // 공백 추가 (시작 요일 맞추기)
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < firstDayOfWeek; i++) {
            dayList.add("");
        }

        for (int i = 1; i <= daysInMonth; i++) {
            dayList.add(String.valueOf(i));
        }

        calendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        calendarAdapter = new CalendarAdapter(dayList, calendar, this);
        calendarRecyclerView.setAdapter(calendarAdapter);

        planMemo.setText(calendarAdapter.getPlanMemo());
        carryMemo.setText(calendarAdapter.getCarryMemo());
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListeners();
        memoStorage = new MemoStorage(this);
    }

    private void initViews() {
        calendarView = findViewById(R.id.calendarView);
        planMemo = findViewById(R.id.planMemo);
        carryMemo = findViewById(R.id.carryMemo);
        btnSave = findViewById(R.id.btnSave);
        planTotal = findViewById(R.id.planTotal);
        carryTotal = findViewById(R.id.carryTotal);
    }

    private void initListeners() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = formatDate(year, month, dayOfMonth);
            String memo = memoStorage.getMemo(selectedDate);
            if (!memo.equals("")) {
                String plan = memo.split(",")[0];
                planMemo.setText(plan);
                String carry = memo.split(",")[1];
                carryMemo.setText(carry);
            } else {
                planMemo.setText("");
                carryMemo.setText("");
            }

            String planTotalStr = memoStorage.getPlanTotal(year, month);
            if (planTotalStr.equals("")) {
                planTotal.setText("계획 합계: ");
            } else {
                planTotal.setText("계획 합계: " + planTotalStr);
            }
            String carryTotalStr = memoStorage.getCarryTotal(year, month);
            if (carryTotalStr.equals("")) {
                carryTotal.setText("사용 합계: ");
            } else {
                carryTotal.setText("사용 합계: " + carryTotalStr);
            }
        });

        btnSave.setOnClickListener(v -> {
            if (selectedDate == null) {
                showToast("날짜를 선택하세요");
                return;
            }
            int plan = Integer.parseInt(planMemo.getText().toString());
            int carry = Integer.parseInt(carryMemo.getText().toString());
            memoStorage.saveMemo(selectedDate, plan + "," + carry);
            showToast("메모 저장됨");
        });
    }

    private String formatDate(int year, int month, int day) {
        return year + "-" + (month + 1) + "-" + day;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

     */
}