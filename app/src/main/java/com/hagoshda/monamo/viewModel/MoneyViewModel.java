package com.hagoshda.monamo.viewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hagoshda.monamo.callback.OnMemoSavedListener;
import com.hagoshda.monamo.model.MoneyMemo;

public class MoneyViewModel {

    private static String PREF_NAME = "MoneyMeno";
    private final SharedPreferences preferences;
    private Context context;

    public MoneyViewModel(Context context, String pref_name) {
        this.context = context;
        this.PREF_NAME = pref_name;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveMemo(MoneyMemo moneyMemo) {
        preferences.edit().putString(moneyMemo.getDay(), moneyMemo.getMoney()).apply();
    }

    public MoneyMemo getMemo(String date) {
        if (preferences.getAll().isEmpty()) {
            return new MoneyMemo();
        }
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

    public void showMemoDialog(String dateKey, OnMemoSavedListener listener) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dateKey + " 추가");

        layout.setPadding(50, 40, 50, 10);
        final EditText plan = new EditText(context);
        final EditText carry = new EditText(context);

        if (getMemo(dateKey).getPlanMoney() != 0) {
            plan.setText(String.valueOf(getMemo(dateKey).getPlanMoney()));
        }

        if (getMemo(dateKey).getCarryMoney() != 0) {
            carry.setText(String.valueOf(getMemo(dateKey).getCarryMoney()));
        }

        layout.addView(plan);
        layout.addView(carry);

        builder.setView(layout);

        builder.setPositiveButton("저장", (dialog, which) -> {
            String planMemo = plan.getText().toString();
            if (planMemo.isEmpty()) {
                planMemo = "0";
            }
            String carryMemo = carry.getText().toString();
            if (carryMemo.isEmpty()) {
                carryMemo = "0";
            }
            MoneyMemo moneyMemo = new MoneyMemo(dateKey, Integer.parseInt(planMemo), Integer.parseInt(carryMemo));
            saveMemo(moneyMemo);
            listener.onMemoSaved();
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }

}
