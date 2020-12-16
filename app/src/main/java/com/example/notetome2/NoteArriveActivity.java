package com.example.notetome2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoteArriveActivity extends AppCompatActivity {

    private TextView lText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notearrive);

        lText = findViewById(R.id.text_letter);

        String note = getIntent().getStringExtra("note");

        lText.setText(note);


        //DIARY 버튼으로 DairyActivity 로 이동
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.btn_diarypage);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
