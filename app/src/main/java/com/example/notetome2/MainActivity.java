package com.example.notetome2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //splash. theme 복구

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //일기 모음 화면으로 가는 버튼
        ImageButton imageButton = (ImageButton)findViewById(R.id.diary_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Diary.class);
                startActivity(intent);
            }
        });
        //설정 화면으로 가는 버튼
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.setting_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
            }
        });


//        vp = (ViewPager) findViewById(R.id.vp);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0); //앱 실행시 맨 처음 페이지로 이동
    }
    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FirstIntroFragment();
                case 1:
                    return new SecondIntroFragment();
                case 2:
                    return new ThirdIntroFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}

//public class MainActivity extends AppCompatActivity {
//
//    private ViewPager viewPager ;
//    private IntroFragment introFragment ;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme); //splash. theme 복구
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);;
//        introFragment = new IntroFragment(this) ;
//        viewPager.setAdapter(introFragment) ;
//
//    }
//}
//


