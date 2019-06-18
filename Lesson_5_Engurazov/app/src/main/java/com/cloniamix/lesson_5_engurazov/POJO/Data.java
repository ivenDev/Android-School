package com.cloniamix.lesson_5_engurazov.POJO;

import android.os.Parcel;
import android.os.Parcelable;


public class Data implements Parcelable {

    private String value;


    public Data(String value) {
        this.value = value;
    }

    private Data(Parcel in){
        value = in.readString();


    }

    public String getValue() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>(){
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
