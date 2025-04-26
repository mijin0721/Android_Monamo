package com.hagoshda.monamo.ViewModel;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

public class CalenderAdapterViewModel {

    private Context context;

    private Calendar calendar;
    private int before_month;
    private boolean check = false;

    public CalenderAdapterViewModel(Calendar calendar, Context context, int month) {
        this.calendar = calendar;
        this.context = context;
    }

    public boolean checkMonth(int month) {
        if (!check) {
            this.before_month = month;
            check = true;
            return true;
        }
        if (before_month != month) {
            this.before_month = month;
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> initCalender(int year, int month) {
        ArrayList<String> dayList = new ArrayList<>();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 공백 추가 (시작 요일 맞추기)
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < firstDayOfWeek; i++) {
            dayList.add(String.valueOf(0));
        }

        for (int i = 1; i <= daysInMonth; i++) {
            dayList.add(String.valueOf(i));
        }

        int size = dayList.size();

        for (int i = size; i<42; i++) {
            dayList.add(String.valueOf(0));
        }
        return dayList;
    }

    public int getWeekendPerMonth(int year, int month) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return (daysInMonth + firstDayOfWeek + 6) / 7;
    }
}
