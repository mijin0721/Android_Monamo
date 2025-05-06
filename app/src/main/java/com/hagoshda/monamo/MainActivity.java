package com.hagoshda.monamo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.navigation.NavigationView;
import com.hagoshda.monamo.adapter.CalenderDayAdapter;
import com.hagoshda.monamo.adapter.CalenderMonthAdapter;
import com.hagoshda.monamo.adapter.CalenderWeekAdapter;
import com.hagoshda.monamo.model.MemoList;
import com.hagoshda.monamo.types.SelectDate;
import com.hagoshda.monamo.viewModel.MemoListViewModel;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView calendarRecyclerView;
    private TextView tvYearMonth;
    private TextView memoTitleTv;
    private CalenderMonthAdapter calenderMonthAdapter;
    private CalenderWeekAdapter calenderWeekAdapter;
    private CalenderDayAdapter calenderDayAdapter;

    private LinearLayout weekend_name_ll;
    private ImageButton today_ib;
    private Button selectDay_tb;
    private Button selectWeek_tb;
    private Button selectMonth_tb;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private Menu menuBar;

    private Calendar calendar;

    private int lastDx = 0;
    private MemoListViewModel memoListViewModel;
    private SelectDate selectDate;

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectDate = SelectDate.MONTH;

        if (context == null) {
            context = this;
        }

        calendar = Calendar.getInstance();

        memoListViewModel = new MemoListViewModel(this);

        initView();
        setNavigationBar();
        setCalenderRecycler();
        detectCalenderRecycler();
        TodayOnClick();
        SelectOnClick();
    }
    
    public void setTextViewYearMonth(int year, int month) {
        tvYearMonth.setText(year+"년 " + month+ "월");
    }
    
    public void setTextViewYearMonthWeek(int year, int month, int week) {
        ++week;
        tvYearMonth.setText(year+"년 " + month+ "월 " + week + "주");
    }

    public void setMemoTitle(String title) {
        memoTitleTv.setText(title);
    }

    private void setNavigationBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        openDrawerButton = findViewById(R.id.open_drawer_button);
        menuBar =  navigationView.getMenu();

        navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        MemoList[] memoLists = memoListViewModel.getMemoListTitle();
        for (int i = 0; i < memoListViewModel.getDBSize(); i++) {
            menuBar.add(Menu.NONE, i, Menu.NONE, memoLists[i].getTitle());
        }

        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDate = SelectDate.MONTH;
                lastDx = 0;
                calenderMonthAdapter.setToday();
                calenderMonthAdapter = new CalenderMonthAdapter(calendar, context, memoListViewModel.getMemoList(0));
                calendarRecyclerView.setAdapter(calenderMonthAdapter);
                calenderMonthAdapter.setMemoList(item.getItemId());
                calendarRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void TodayOnClick() {
        today_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDate == SelectDate.MONTH) {
                    calenderMonthAdapter.setToday();
                } else if (selectDate == SelectDate.WEEK) {
                    calenderWeekAdapter.setToday();
                } else if (selectDate == SelectDate.DAY) {
                    calenderDayAdapter.setToday();
                }
            }
        });
    }

    private void SelectOnClick() {
        selectDay_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDate != SelectDate.DAY) {
                    selectDate = SelectDate.DAY;
                    weekend_name_ll.setVisibility(View.GONE);
                    calendarRecyclerView.setAdapter(calenderDayAdapter);
                    calendarRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
                }
            }
        });

        selectWeek_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDate != SelectDate.WEEK) {
                    selectDate = SelectDate.WEEK;
                    weekend_name_ll.setVisibility(View.GONE);
                    calendarRecyclerView.setAdapter(calenderWeekAdapter);
                    calendarRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
                }
            }
        });

        selectMonth_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectDate != SelectDate.MONTH) {
                    selectDate = SelectDate.MONTH;
                    weekend_name_ll.setVisibility(View.VISIBLE);
                    calendarRecyclerView.setAdapter(calenderMonthAdapter);
                    calendarRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
                }
            }
        });
    }

    private void initView() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        tvYearMonth = findViewById(R.id.tv_year_month);
        memoTitleTv = findViewById(R.id.memo_title_tv);
        weekend_name_ll = findViewById(R.id.weekend_name_ll);
        today_ib = findViewById(R.id.today_ib);
        selectDay_tb = findViewById(R.id.select_day_bt);
        selectWeek_tb = findViewById(R.id.select_week_bt);
        selectMonth_tb = findViewById(R.id.select_month_bt);
    }

    private void setCalenderRecycler() {
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        calenderMonthAdapter = new CalenderMonthAdapter(calendar, this, memoListViewModel.getMemoList(0));
        calenderWeekAdapter = new CalenderWeekAdapter(calendar, this, memoListViewModel.getMemoList(0));
        calenderDayAdapter = new CalenderDayAdapter(calendar, this, memoListViewModel.getMemoList(0));
        calendarRecyclerView.setAdapter(calenderMonthAdapter);
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
                        if (selectDate == SelectDate.MONTH) {
                            calenderMonthAdapter.setMonthP();
                        } else if (selectDate == SelectDate.WEEK) {
                            calenderWeekAdapter.setWeekP();
                        } else if (selectDate == SelectDate.DAY) {
                            calenderDayAdapter.setWeekP();
                        }
                    } else if (lastDx < 0) {
                        if (selectDate == SelectDate.MONTH) {
                            calenderMonthAdapter.setMonthM();
                        } else if (selectDate == SelectDate.WEEK) {
                            calenderWeekAdapter.setWeekM();
                        } else if (selectDate == SelectDate.DAY) {
                            calenderDayAdapter.setWeekM();
                        }
                    }
                }
            }
        });
    }
}