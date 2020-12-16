package com.example.notetome2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DiaryActivity extends AppCompatActivity {

    private DiaryListAdapter nAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        RecyclerView rv = (RecyclerView) findViewById(R.id.diary_list);
        Cursor cursor = getCursor();

        // RecyclerView 의 Decoration
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(rv.getContext(),new LinearLayoutManager(this).getOrientation());
        rv.addItemDecoration(dividerItemDecoration);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        rv.addItemDecoration(spaceDecoration);

        ArrayList<Diary> dataList = new ArrayList<Diary>();
        while (cursor.moveToNext()) {
            Diary diary = new Diary();
            diary.diary = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_diary));
            diary.note = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_note));
            diary.alarm = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_alarm));
            diary.writeDate = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_writeDate));
            diary.date = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_date));
            dataList.add(diary);
        }
        nAdapter = new DiaryListAdapter(this, dataList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(nAdapter);

        //home
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btn_back);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if (getIntent().getBooleanExtra("EXIT_ROOT", false)) {
            this.finishAndRemoveTask();
            System.exit(0);
        }

    }

    private Cursor getCursor() {
        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(NoteContract.NoteEntry.TABLE_NAME,
                null, null, null, null, null, null);
    }
}