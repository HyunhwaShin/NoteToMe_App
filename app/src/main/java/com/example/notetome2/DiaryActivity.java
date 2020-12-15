package com.example.notetome2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;


public class DiaryActivity extends AppCompatActivity {

    private DiaryListAdapter nAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        RecyclerView rv = (RecyclerView) findViewById(R.id.diary_list);
        Cursor cursor = getCursor();

        ArrayList<String> dataList = new ArrayList<String>();
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_diary));
            dataList.add(data);
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