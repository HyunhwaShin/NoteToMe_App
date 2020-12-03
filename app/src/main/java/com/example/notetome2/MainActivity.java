package com.example.notetome2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity<val> extends AppCompatActivity {
//    MyPagerAdapter adapterViewPager;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM-dd-EE");
    String formatDate = sdfNow.format(date);
    TextView dateNow;

    EditText editText;
    EditText editText2;

    SQLiteDatabase database;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //splash. theme 복구

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SQlite 연동
        editText = findViewById(R.id.diary_editText);
        editText2 = findViewById(R.id.note_editText);

        //오늘의 날짜 표시
        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당

        //spinner
        final String[] data = getResources().getStringArray(R.array.date_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


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
                startActivity(intent);
            }
        });
    }
}
//***********지우면 안됌 !!!!ViewPager 잠시 보류해놓은 것
//        ViewPager vp = (ViewPager) findViewById(R.id.vp);
//        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
//        vp.setAdapter(adapterViewPager);
//        vp.setCurrentItem(0); //앱 실행시 맨 처음 페이지로 이동
//
//    public static class MyPagerAdapter extends FragmentPagerAdapter {
//        private static int NUM_ITEMS = 3;
//
//         public MyPagerAdapter(FragmentManager fm) {
//              super(fm);
//         }
//
//    @Override
//        public int getCount() {
//         return NUM_ITEMS;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    return new FirstIntroFragment();
//                case 1:
//                    return new SecondIntroFragment();
//                case 2:
//                    return new ThirdIntroFragment();
//                default:
//                    return null;
//            }
//        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "Page " + position;
//          }


