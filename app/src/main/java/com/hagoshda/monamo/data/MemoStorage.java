package com.hagoshda.monamo.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MemoStorage {
    private static final String PREF_NAME = "MemoApp";
    private final SharedPreferences preferences;

    public MemoStorage(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveMemo(String date, String memo) {
        preferences.edit().putString(date, memo).apply();
    }

    public String getMemo(String date) {
        return preferences.getString(date, "");
    }

    public String getPlanTotal(int year, int month) {
        int planTotal = 0;
        for (int i = 0; i < 31; i++) {
            String selectedDate = formatDate(year, month, i);
            String memo = getMemo(selectedDate);
            if (memo.equals("")) {
                continue;
            }
            int plan = Integer.parseInt(memo.split(",")[0]);
            planTotal = planTotal + plan;
        }
        return String.valueOf(planTotal);
    }

    public String getCarryTotal(int year, int month) {
        int carryTotal = 0;
        for (int i = 0; i < 31; i++) {
            String selectedDate = formatDate(year, month, i);
            String memo = getMemo(selectedDate);
            if (memo.equals("")) {
                continue;
            }
            int carry = Integer.parseInt(memo.split(",")[1]);
            carryTotal = carryTotal + carry;
        }
        return String.valueOf(carryTotal);
    }

    public String getPlanDate(int year, int month, int date) {
        String selectedDate = formatDate(year, month, date);
        String memo = getMemo(selectedDate);
        if (memo.isEmpty()) {
            return "0";
        }
        int plan = Integer.parseInt(memo.split(",")[0]);
        Log.d("", date + "/" + plan);
        return String.valueOf(plan);
    }

    public String getCarryDate(int year, int month, int date) {
        String selectedDate = formatDate(year, month, date);
        String memo = getMemo(selectedDate);
        if (memo.isEmpty()) {
            return "0";
        }
        int carry = Integer.parseInt(memo.split(",")[1]);
        return String.valueOf(carry);
    }

    public String formatDate(int year, int month, int day) {
        return year + "-" + (month + 1) + "-" + day;
    }
}