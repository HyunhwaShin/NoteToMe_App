package com.example.notetome2;

import android.provider.BaseColumns;

public class NoteContract {

    private NoteContract(){}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME ="nNote";
        public static final String COLUMN_diary ="diary";
        public static final String COLUMN_note ="note";
    }
}