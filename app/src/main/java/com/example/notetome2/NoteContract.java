package com.example.notetome2;

import android.provider.BaseColumns;

public class NoteContract {

    private NoteContract(){}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME ="nNote";
        public static final String COLUMN_diary ="diary"; //editText
        public static final String COLUMN_note ="note"; //editText
        public static final String COLUMN_writeDate ="writeDate"; //작성날짜
        public static final String COLUMN_date = "date"; //오늘날짜+spinner = 도착 날짜
        public static final String COLUMN_alarm = "alarm"; //알람 시간
    }
}