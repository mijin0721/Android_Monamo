package com.hagoshda.monamo;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hagoshda.monamo.Adapter.CalenderAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView calendarRecyclerView;
    private TextView tvYearMonth;
    private CalenderAdapter calenderAdapter;

    private ImageButton today_ib;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;

    private Calendar calendar;

    private int lastDx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        initView();
        setNavigationBar();
        setCalenderRecycler();
        detectCalenderRecycler();
        TodayOnClick();
    }
    
    public void setTextViewYearMonth(int year, int month) {
        tvYearMonth.setText(year+"년 " + month+ "월");
    }

    private void setNavigationBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        openDrawerButton = findViewById(R.id.open_drawer_button);

        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 메뉴 클릭 시 동작
                if (item.toString().equals("메뉴 1")) {
                    Toast.makeText(MainActivity.this, "메뉴 1 선택됨", Toast.LENGTH_SHORT).show();
                } else if (item.toString().equals("메뉴 2")) {
                    Toast.makeText(MainActivity.this, "메뉴 2 선택됨", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void TodayOnClick() {
        today_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderAdapter.setToday();
            }
        });
    }

    private void initView() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        tvYearMonth = findViewById(R.id.tv_year_month);
        today_ib = findViewById(R.id.today_ib);
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