package com.hagoshda.monamo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.hagoshda.monamo.Adapter.CalenderAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView calendarRecyclerView;
    private TextView tvYearMonth;
    private CalenderAdapter calenderAdapter;

    private Calendar calendar;

    private int lastDx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        initView();
        setCalenderRecycler();
        detectCalenderRecycler();
    }
    
    public void setTextViewYearMonth(int year, int month) {
        tvYearMonth.setText(year+"년 " + month+ "월");
    }

    private void initView() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        tvYearMonth = findViewById(R.id.tv_year_month);
    }

    private void setCalenderRecycler() {
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        calenderAdapter = new CalenderAdapter(calendar, this);
        calendarRecyclerView.setAdapter(calenderAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        calendarRecyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(calendarRecyclerView);
        calendarRecyclerView.setHorizontalScrollBarEnabled(false);
        calendarRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
    }

    private void detectCalenderRecycler() {
        calendarRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx != 0) {
                    lastDx = dx;
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastDx > 0) {
                        calenderAdapter.setMonthP();
                    } else if (lastDx < 0) {
                        calenderAdapter.setMonthM();
                    }
                }
            }
        });
    }
}