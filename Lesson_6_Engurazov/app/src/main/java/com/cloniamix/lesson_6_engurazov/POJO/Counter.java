package com.cloniamix.lesson_6_engurazov.POJO;


public class Counter {

    private String counterName;
    private String counterNumber;
    private int iconResId;
    private boolean singleType;
    private boolean alarm;
    private String alarmDay;
    private String readingPassedDay;
    /*private int alarmTextResId;*/


    //region constructor, getters & setters

    public Counter(String counterName
            , String counterNumber
            , int iconResId
            , boolean singleType
            , boolean alarm
            , String alarmDay) {

        this.counterName = counterName;
        this.counterNumber = counterNumber;
        this.iconResId = iconResId;
        this.singleType = singleType;
        this.alarm = alarm;
        this.alarmDay = alarmDay;
        /*this.alarmTextResId = alarmTextResId;*/
    }
    public Counter(String counterName
            , String counterNumber
            , int iconResId
            , boolean singleType
            , boolean alarm
            , String alarmDay
            , String readingPassedDay) {

        this.counterName = counterName;
        this.counterNumber = counterNumber;
        this.iconResId = iconResId;
        this.singleType = singleType;
        this.alarm = alarm;
        this.alarmDay = alarmDay;
        this.readingPassedDay = readingPassedDay;
        /*this.alarmTextResId = alarmTextResId;*/
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

    public String getAlarmDay() {
        return alarmDay;
    }

    public String getReadingPassedDay() {
        return readingPassedDay;
    }


    /*public int getAlarmTextResId() {
        return alarmTextResId;
    }*/
    //endregion
}
