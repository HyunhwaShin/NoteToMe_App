package com.example.notetome2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private PrefManager prefManager;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM-dd-EE");
    String formatDate = sdfNow.format(date);
    TextView dateNow;

    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //최초 실행 판별
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.diary_editText);
        editText2 = findViewById(R.id.note_editText);

        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(MainActivity.this, "diary.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        //spinner
        final String[] data= getResources().getStringArray(R.array.date_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,data);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        //오늘의 날짜 표시
        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당

        //일기 모음 화면으로 가는 버튼
        ImageButton imageButton = (ImageButton) findViewById(R.id.diary_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Diary.class);
                startActivity(intent);
            }
        });
        //설정 화면으로 가는 버튼
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.setting_imageButton);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);  }
        });
    }

}
