package com.hagoshda.monamo.model;

public class MoneyMemo {
    private String day = "";
    private int planMoney = 0;
    private int carryMoney = 0;
    private String Money = "";

    public MoneyMemo() {
        this.day = "";
        this.planMoney = 0;
        this.carryMoney = 0;
        this.Money = this.planMoney + "," + this.carryMoney;
    }

    public MoneyMemo(String day, int planMoney, int carryMoney) {
        this.day = day;
        this.planMoney = planMoney;
        this.carryMoney = carryMoney;
        setMoney(planMoney, carryMoney);
    }

    public MoneyMemo(String day, String money) {
        this.day = day;
        this.Money = money;
        setPlanCarry(money);
    }

    public void setDay(int year, int month, int day) {
        this.day = formatDate(year,month,day);
    }

    public String getDay() {
        return this.day;
    }

    public void setPlanMoney(int planMoney) {
        this.planMoney = planMoney;
    }

    public int getPlanMoney() {
        return this.planMoney;
    }

    public void setCarryMoney(int carryMoney) {
        this.carryMoney = carryMoney;
    }

    public int getCarryMoney() {
        return carryMoney;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(int planMoney, int carryMoney) {
        this.Money = planMoney + "," + carryMoney;
    }

    public void setPlanCarry(String money) {
        if (money.isEmpty()) {
            return;
        }
        this.planMoney = Integer.parseInt(money.split(",")[0]);
        this.carryMoney = Integer.parseInt(money.split(",")[1]);
    }

    public static String formatDate(int year, int month, int day) {
        return year + "-" + (month + 1) + "-" + day;
    }
}
