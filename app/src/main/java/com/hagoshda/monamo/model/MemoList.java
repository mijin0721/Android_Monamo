package com.hagoshda.monamo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemoList {

    private int count = 0;
    private String title = "";
    private MemoType memoType = MemoType.NONE;
    private String createdOn;
    private String dbName;

    public MemoList() {
        this.count = 0;
        this.title = "";
        this.createdOn = "";
        this.memoType = MemoType.NONE;
        this.dbName = "";
    }

    public MemoList(int i, String date, int memoType, String title, String dbName) {
        this.count = i;
        this.title = title;
        this.memoType = MemoType.getMemoType(memoType);
        this.createdOn = date;
        this.dbName = dbName;
    }

    public MemoList(int i, String title, MemoType memoType) {
        this.count = i;
        this.title = title;
        this.memoType = memoType;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.createdOn = now.format(formatter);
        this.dbName = memoType.toString() + this.count;
    }

    public MemoList(String str) {
        if (str.isEmpty()) {
            new MemoList();
        }
        this.title = str.split(",")[0];
        this.memoType = MemoType.getMemoType(Integer.parseInt(str.split(",")[1]));
        this.createdOn = str.split(",")[2];
        this.dbName = str.split(",")[3];
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MemoType getMemoType() {
        return memoType;
    }

    public void setMemoType(MemoType memoType) {
        this.memoType = memoType;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public enum MemoType {
        JOURNAL,
        HABIT,
        EXPENSE_TRACKER,
        IMAGE,
        NONE;

        public static MemoType getMemoType(int i) {
            switch (i) {
                case 0 : return JOURNAL;
                case 1 : return HABIT;
                case 2 : return EXPENSE_TRACKER;
                case 3 : return IMAGE;
                default: return NONE;
            }
        }
    }
}
