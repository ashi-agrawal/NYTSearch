package com.example.ashiagrawal.nytsearch;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by ashiagrawal on 6/24/16.
 */
public class FilterInfo implements Serializable{
    public Calendar date;
    String order;
    boolean arts = false;
    boolean fashion = false;
    boolean sports = false;

    public String toString(){
        String date = "";
        if (this.date != null) {
            date = String.valueOf(this.date.get(Calendar.DAY_OF_MONTH)) + String.valueOf(this.date.get(Calendar.MONTH)) +
                    String.valueOf(this.date.get(Calendar.YEAR));
        }
        return  date + order + arts + fashion + sports;
    }

//    public void writeToParcel(Parcel out, int flags) {
//        out.writeInt(date.get(Calendar.DAY_OF_MONTH));
//        out.writeInt(date.get(Calendar.MONTH));
//        out.writeInt(date.get(Calendar.YEAR));
//        out.writeString(order);
//        out.writeBooleanArray(checks);
//    }
//
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Parcelable.Creator<FilterInfo> CREATOR
//            = new Parcelable.Creator<FilterInfo>() {
//        public FilterInfo createFromParcel(Parcel in) {
//            return new FilterInfo(in);
//        }
//        public FilterInfo[] newArray(int size) {
//            return new FilterInfo[size];
//        }
//    };
//
//    public FilterInfo(){
//
//    }
//
//    private FilterInfo(Parcel in) {
//        int day = in.readInt();
//        int month = in.readInt();
//        int year = in.readInt();
//        date.set(year, month, day);
//        order = in.readString();
//        in.readBooleanArray(checks);
//    }

    public boolean isArts() {
        return arts;
    }

    public String getDateLabel() {
        String date = String.valueOf(this.date.get(Calendar.YEAR)) + "-" + String.valueOf(this.date.get(Calendar.MONTH))
                + "-" + String.valueOf(Calendar.DAY_OF_MONTH);
        return date;
    }

    public String getDateFilter() {
        String date = String.valueOf(this.date.get(Calendar.YEAR)) + String.valueOf(this.date.get(Calendar.MONTH))
                + String.valueOf(Calendar.DAY_OF_MONTH);
        return date;
    }

    public String getOrder() {
        return order;
    }

    public boolean isSports() {
        return sports;
    }

    public boolean isFashion() {
        return fashion;
    }

    public boolean setArts(boolean arts) {
this.arts = arts;
        return arts;
    }

    public String setOrder(String filter) {
        order = filter;
        return order;
    }

    public boolean setFashion(boolean fashion) {
        this.fashion = fashion;
        return fashion;
    }
    public boolean setSports(boolean sports) {
        this.sports = sports;
        return sports;
    }}
