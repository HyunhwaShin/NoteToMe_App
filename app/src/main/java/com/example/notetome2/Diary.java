package com.example.notetome2;

import android.os.Parcel;
import android.os.Parcelable;

public class Diary implements Parcelable {
    String diary = "";
    String note = "";
    String date = "";
    String alarm = "";
    String writeDate="";

    protected Diary(Parcel in) {
        diary = in.readString();
        note = in.readString();
        date = in.readString();
        alarm = in.readString();
        writeDate = in.readString();
    }

    public Diary() {

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diary);
        dest.writeString(note);
        dest.writeString(date);
        dest.writeString(alarm);
        dest.writeString(writeDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };


}
