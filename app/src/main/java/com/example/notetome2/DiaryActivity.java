package com.example.notetome2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiaryActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_INSERT =1000;
    private NoteAdapter nAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diary);
        ListView listView = (ListView) findViewById(R.id.diary_list);
        listView.setAdapter(nAdapter);
        Cursor cursor = getMemoCursor();
        nAdapter = new NoteAdapter(this, cursor);


        //리스트 클릭했을때 내용 보이게
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DiaryActivity.this, MainActivity.class);

                Cursor cursor = (Cursor) nAdapter.getItem(position);

                String diary = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_diary));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_note));

                intent.putExtra("id", id);
                intent.putExtra("diary", diary);
                intent.putExtra("note", note);

                startActivityForResult(intent,REQUEST_CODE_INSERT);
            }
        });

        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btn_back);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);  }
        });


    }
    //DB 에서 조회
    private  Cursor getMemoCursor(){
        NoteDBHelper dbHelper = NoteDBHelper.getInstance(this);
        return  dbHelper.getReadableDatabase().query(NoteContract.NoteEntry.TABLE_NAME,
                null,null,null,null,null,null);
    }

    //메모 작성 후 최신 내용
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==REQUEST_CODE_INSERT && resultCode == RESULT_OK){
            nAdapter.swapCursor(getMemoCursor());
        }
    }

    //리스트뷰
    static class NoteAdapter extends CursorAdapter {
        public NoteAdapter(Context context, Cursor c){
            super(context,c);
        }
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
        }

        //데이터
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView diaryText = view.findViewById(android.R.id.text1);
            diaryText.setText(cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_diary)));

        }
    }
}
