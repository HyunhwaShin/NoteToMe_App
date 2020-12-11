package com.example.notetome2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private PrefManager prefManager;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM-dd-EE");
    String formatDate = sdfNow.format(date);
    TextView dateNow;

    private EditText dEditText;
    private EditText nEditText;
    private long nNoteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //최초 실행 판별
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch() == true) {
            Log.d("In MainActivity", "goto Intro Screen");
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);


        dEditText = findViewById(R.id.diary_editText);
        nEditText = findViewById(R.id.note_editText);

        Intent intent = getIntent();
        if (intent != null) {
            nNoteId = intent.getLongExtra("id", -1);
            String diary = intent.getStringExtra("diary");
            String note = intent.getStringExtra("note");

            dEditText.setText(diary);
            nEditText.setText(note);
        }

        //spinner
        final String[] data = getResources().getStringArray(R.array.date_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        //오늘의 날짜 표시
        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);    // TextView 에 현재 시간 문자열 할당

        //일기 모음 화면으로 가는 버튼
        ImageButton imageButton = (ImageButton) findViewById(R.id.diary_imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(intent);
            }
        });
        //설정 화면으로 가는 버튼
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.setting_imageButton);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    //뒤로 가기 눌렀을 때 저장
    @Override
    public void onBackPressed() {
        String diary = dEditText.getText().toString();
        String note = nEditText.getText().toString();

        //SQLite 에 저장
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteContract.NoteEntry.COLUMN_diary,diary);
        contentValues.put(NoteContract.NoteEntry.COLUMN_note,note);

        //DB 에 전달
        SQLiteDatabase db = NoteDBHelper.getInstance(this).getWritableDatabase();

        if(nNoteId == -1) {
            long newRowID = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, contentValues);

            if (newRowID == -1) {
                //Toast.makeText(this, "저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }else {
            int count = db.update(NoteContract.NoteEntry.TABLE_NAME, contentValues, NoteContract.NoteEntry._ID+"="+nNoteId,null);
            if(count ==0){
                //Toast.makeText(this, "수정되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }
        super.onBackPressed();
    }

}
