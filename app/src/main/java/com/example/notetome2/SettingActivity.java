package com.example.notetome2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

 @Override
 protected void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_setting);

  final TimePicker mTimePicker = (TimePicker) findViewById(R.id.time_picker);
  mTimePicker.setIs24HourView(true);

  // 앞서 설정한 값. 없으면 디폴트 값은 현재시간
  SharedPreferences sharedPreferences = getSharedPreferences("alarm", MODE_PRIVATE);
  long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

  Calendar nextNotifyTime = new GregorianCalendar();
  nextNotifyTime.setTimeInMillis(millis);

  Date nextDate = nextNotifyTime.getTime();
  String date_text = new SimpleDateFormat("a hh시 mm분 ", Locale.getDefault()).format(nextDate);//yyyy년 MM월 dd일 EE요일
  Toast.makeText(getApplicationContext(), "현재 설정 되어있는 알람 시간은 " + date_text + "입니다", Toast.LENGTH_LONG).show();

  // 이전 설정값으로 TimePicker 초기화
  Date currentTime = nextNotifyTime.getTime();
  SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
  SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

  int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
  int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));


  if (Build.VERSION.SDK_INT >= 23) {
   mTimePicker.setHour(pre_hour);
   mTimePicker.setMinute(pre_minute);
  } else {
   mTimePicker.setCurrentHour(pre_hour);
   mTimePicker.setCurrentMinute(pre_minute);
  }

  Button button = (Button) findViewById(R.id.btn_setting);
  button.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View arg0) {

    int hour, hour_24, minute;
    String am_pm;
    if (Build.VERSION.SDK_INT >= 23) {
     hour_24 = mTimePicker.getHour();
     minute = mTimePicker.getMinute();
    } else {
     hour_24 = mTimePicker.getCurrentHour();
     minute = mTimePicker.getCurrentMinute();
    }
    if (hour_24 > 12) {
     am_pm = "PM";
     hour = hour_24 - 12;
    } else {
     hour = hour_24;
     am_pm = "AM";
    }

    // 현재 지정된 시간으로 알람 시간 설정
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, hour_24);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, 0);

    // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
    if (calendar.before(Calendar.getInstance())) {
     calendar.add(Calendar.DATE, 1);
    }

    Date currentDateTime = calendar.getTime();
    String date_text = new SimpleDateFormat("a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
    Toast.makeText(getApplicationContext(), date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_LONG).show();

    //Preference 에 설정한 값 저장
    SharedPreferences.Editor editor = getSharedPreferences("alarm", MODE_PRIVATE).edit();
    editor.putLong("nextNotifyTime", (long) calendar.getTimeInMillis());
    editor.apply();


    diaryNotification(calendar);
   }

  });

  //back
  ImageButton imageButton4 = (ImageButton) findViewById(R.id.btn_back);
  imageButton4.setOnClickListener(new View.OnClickListener() {
   public void onClick(View v) {
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(intent);
   }
  });
 }


 void diaryNotification(Calendar calendar) {

  Boolean dailyNotify = true; // 무조건 알람을 사용

  PackageManager pm = this.getPackageManager();
  ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
  Intent alarmIntent = new Intent(this, AlarmReceiver.class);
  PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
  AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


  // 매일 알람 허용시
  if (dailyNotify) {

   if (alarmManager != null) {

    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY, pendingIntent);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
     alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
   }
   // 부팅 후 실행되는 receiver 사용가능하게 설정
   pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
  }
 }

}