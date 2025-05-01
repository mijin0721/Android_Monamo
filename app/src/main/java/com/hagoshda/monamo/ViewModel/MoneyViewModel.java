package com.hagoshda.monamo.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.hagoshda.monamo.Model.MoneyMemo;

public class MoneyViewModel {

    private static final String PREF_NAME = "MoneyMeno";
    private final SharedPreferences preferences;

    public MoneyViewModel(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveMemo(MoneyMemo moneyMemo) {
        preferences.edit().putString(moneyMemo.getDay(), moneyMemo.getMoney()).apply();
    }

    public MoneyMemo getMemo(String date) {
        return new MoneyMemo(date, preferences.getString(date, ""));
    }

    public int getMonthPlan(int year, int month) {
        int totalPlan = 0;
        for (int i = 0; i <= 31; i++) {
            String data = MoneyMemo.formatDate(year, month, i);
            totalPlan = totalPlan + getMemo(data).getPlanMoney();
        }
        return totalPlan;
    }

    public int getMonthPlanToToday(int year, int month, int day) {
        int totalPlan = 0;
        for (int i = 0; i <= day; i++) {
            String data = MoneyMemo.formatDate(year, month, i);
            totalPlan = totalPlan + getMemo(data).getPlanMoney();
        }
        return totalPlan;
    }

    public int getMonthCarry(int year, int month) {
        int totalCarry = 0;
        for (int i = 0; i <= 31; i++) {
            String data = MoneyMemo.formatDate(year, month, i);
            totalCarry = totalCarry + getMemo(data).getCarryMoney();
        }
        return totalCarry;
    }
}
