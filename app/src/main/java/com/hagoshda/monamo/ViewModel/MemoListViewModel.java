package com.hagoshda.monamo.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.hagoshda.monamo.Model.MemoList;

public class MemoListViewModel {
    private static final String PREF_NAME = "MenoList";
    private final SharedPreferences preferences;

    public MemoListViewModel(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        preferences.edit().clear().apply();
//        if (preferences.getAll().isEmpty()) {
//            MemoList memoList1 = new MemoList(preferences.getAll().size(),
//                    "한줄 일기", MemoList.MemoType.JOURNAL);
//            saveMemoList(memoList1);
//            MemoList memoList2 = new MemoList(preferences.getAll().size(),
//                    "가계부", MemoList.MemoType.EXPENSE_TRACKER);
//            saveMemoList(memoList2);
//        }
    }

    public MemoList[] getMemoListTitle() {
        MemoList[] titles = new MemoList[preferences.getAll().size()];

        for (int i = 0; i < preferences.getAll().size(); i++) {
            titles[i] = getMemoList(i);
        }

        return titles;
    }

    public int getDBSize() {
        return preferences.getAll().size();
    }

    public void saveMemoList(MemoList memoList) {
        preferences.edit().putString(String.valueOf(memoList.getCount()),
                memoList.getTitle() + "," + memoList.getMemoType().ordinal() + "," + memoList.getCreatedOn() + "," + memoList.getDbName())
                .apply();
    }

    public MemoList getMemoList(int i) {
        return new MemoList(preferences.getString(String.valueOf(i), ""));
    }
}
