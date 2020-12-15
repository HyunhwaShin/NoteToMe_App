package com.example.notetome2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class NoteDBHelper extends SQLiteOpenHelper {
    private static NoteDBHelper sInstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NoteToMe.db";
    private static final String SQL_CREATE_ENTERS =
            String.format(
                    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                    NoteContract.NoteEntry.TABLE_NAME,
                    NoteContract.NoteEntry._ID,
                    NoteContract.NoteEntry.COLUMN_diary,
                    NoteContract.NoteEntry.COLUMN_note);

    public static final String SQL_DELETE_ENTERS =
            "DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME;

    public static NoteDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NoteDBHelper(context);
        }
        return sInstance;
    }

    public NoteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTERS);
        onCreate(db);
    }
}
