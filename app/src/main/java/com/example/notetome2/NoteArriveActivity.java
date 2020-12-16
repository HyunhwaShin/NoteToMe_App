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
    private EditText nEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notearrive);

        nEditText = findViewById(R.id.note_editText);
        lText = findViewById(R.id.text_letter);

        NoteDBHelper noteDBHelper = new NoteDBHelper(this);
        SQLiteDatabase db = noteDBHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select note from nNote order by _id desc limit 1",null);

        while(cursor.moveToNext()){
            lText.setText(cursor.getString(1));
        }
        db.close(); //


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
