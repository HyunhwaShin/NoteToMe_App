package com.example.notetome2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectDiaryActivity extends AppCompatActivity {

    private TextView dateText;
    private TextView contentText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdiary);

        dateText= findViewById(R.id.text_date);
        contentText = findViewById(R.id.text_letter);

        Diary diary = getIntent().getParcelableExtra("diary");

        dateText.setText(diary.writeDate);
        contentText.setText(diary.diary);

        //DIARY 버튼으로 DairyActivity 로 이동
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.btn_diarypage);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(intent);
            }
        });
    }


}
