package com.grhprogramming.bplogv2.data;

public class BP {
    int d_id;
    int d_hour;
    int d_minute;
    int d_month;
    int d_day;
    int d_year;
    int d_systolic;
    int d_dystolic;
    int d_heartrate;

    public BP(int id, int hour, int minute, int month, int day, int year, int systolic, int dystolic, int heartrate) {
        this.d_id = id;
        this.d_hour = hour;
        this.d_minute = minute;
        this.d_month = month;
        this.d_day = day;
        this.d_year = year;
        this.d_systolic = systolic;
        this.d_dystolic = dystolic;
        this.d_heartrate = heartrate;
    }

    public int getId() {
        return d_id;
    }

    public int getHour() {
        return d_hour;
    }

    public int getMinute() {
        return d_minute;
    }

    public int getMonth() {
        return d_month;
    }

    public int getDay() {
        return d_day;
    }

    public int getYear() {
        return d_year;
    }

    public int getSystolic() {
        return d_systolic;
    }

    public int getDystolic() {
        return d_dystolic;
    }

    public int getHeartrate() {
        return d_heartrate;
    }
}
