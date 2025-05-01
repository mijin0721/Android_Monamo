package com.hagoshda.monamo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
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
import com.hagoshda.monamo.Model.MemoList;
import com.hagoshda.monamo.ViewModel.MemoListViewModel;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView calendarRecyclerView;
    private TextView tvYearMonth;
    private TextView memoTitleTv;
    private CalenderAdapter calenderAdapter;

    private ImageButton today_ib;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton openDrawerButton;
    private Menu menuBar;

    private Calendar calendar;

    private int lastDx = 0;
    private MemoListViewModel memoListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        memoListViewModel = new MemoListViewModel(this);

        initView();
        setNavigationBar();
        setCalenderRecycler();
        detectCalenderRecycler();
        TodayOnClick();
    }
    
    public void setTextViewYearMonth(int year, int month) {
        tvYearMonth.setText(year+"년 " + month+ "월");
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
                calenderAdapter.setMemoList(item.getItemId());
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
        memoTitleTv = findViewById(R.id.memo_title_tv);
        today_ib = findViewById(R.id.today_ib);
    }

    private void setCalenderRecycler() {
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        calenderAdapter = new CalenderAdapter(calendar, this, memoListViewModel.getMemoList(0));
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