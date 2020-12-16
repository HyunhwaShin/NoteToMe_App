package com.example.notetome2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.notetome2.DI.DiaryList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.function.Consumer;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {

    private String note;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, NoteArriveActivity.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_app);

            String channelName ="알람 채널";
            String description = "정해진 시간에 알람합니다";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리, 알림메시지를 같이

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel); // notification 채널을 시스템에 등록
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher);

        if (notificationManager != null) {

            // 노티피케이션 동작시킴
            Calendar alarm = Calendar.getInstance();
            SharedPreferences sharedPreferences = context.getSharedPreferences("alarm", MODE_PRIVATE);
            alarm.setTimeInMillis(sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis()));
            String alarm_text = new SimpleDateFormat(" a hh시 mm분 ").format(alarm.getTime());
            for(Diary diary : DiaryList.data){
                Calendar now = Calendar.getInstance();
                String now_date_text = new SimpleDateFormat("yyyy/MM/dd").format(now.getTime());
                if(diary.date.equals(now_date_text) && diary.alarm.trim().replaceAll(" ","").equals(alarm_text.trim().replaceAll(" ",""))){
                    note = diary.note;
                    notificationIntent.putExtra("note",note);

                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

                    builder.setAutoCancel(true)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())

                            .setTicker("{Time to watch some cool stuff!}")
                            .setContentTitle("띵동! 당신이 보낸 미래쪽지가 도착했어요!")
                            .setContentText("얼른 확인하러 가보세요")
                            .setContentInfo("INFO")
                            .setContentIntent(pendingIntent);

                    notificationManager.notify(1234, builder.build());

                }
            }

            Calendar nextNotifyTime = Calendar.getInstance();

            // 내일 같은 시간으로 알람시간 결정
            nextNotifyTime.add(Calendar.DATE, 1);

            //  Preference에 설정한 값 저장
            SharedPreferences.Editor editor = context.getSharedPreferences("alarm", MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

            Date currentDateTime = nextNotifyTime.getTime();
            String date_text = new SimpleDateFormat("a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
            Toast.makeText(context.getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다", Toast.LENGTH_LONG).show();
        }
    }
}