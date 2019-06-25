package com.cloniamix.lesson_6_engurazov.POJO;


public class Counter {

    private String counterName;
    private String counterNumber;
    private int iconResId;
    private boolean singleType;
    private boolean alarm;
    private String alarmText;


    //region constructor, getters & setters

    public Counter(String counterName
            , String counterNumber
            , int iconResId
            , boolean singleType
            , boolean alarm
            , String alarmText) {

        this.counterName = counterName;
        this.counterNumber = counterNumber;
        this.iconResId = iconResId;
        this.singleType = singleType;
        this.alarm = alarm;
        this.alarmText = alarmText;
    }

    public String getCounterName() {
        return counterName;
    }

    public String getCounterNumber() {
        return counterNumber;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isSingleType() {
        return singleType;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public String getAlarmText() {
        return alarmText;
    }
    //endregion
}
