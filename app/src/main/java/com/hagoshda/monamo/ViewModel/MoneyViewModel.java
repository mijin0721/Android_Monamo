package com.hagoshda.monamo.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.hagoshda.monamo.Model.MoneyMemo;

public class MoneyViewModel {

    private static final String PREF_NAME = "MemoApp";
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
}
