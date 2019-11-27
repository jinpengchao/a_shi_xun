package h.jpc.vhome.parents.fragment.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;


import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.R;

import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.list;
import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.timeAdapter;


public class ReadAlarm extends AppCompatActivity {
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_alarm);
//        content = findViewById(R.id.content);
        calendar = Calendar.getInstance();
        //数据库
        Clock clock = new Clock();
        clock.setHour("10");
        clock.setMinute("54");
        clock.setContent("w");
        clock.setClockType(Clock.clock_open);
        list.add(clock);

        Log.e("list",list.toString());
        Intent intent = new Intent(ReadAlarm.this, CallAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                ReadAlarm.this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Log.e("get_hour",clock.getHour());
        Log.e("get_minutes",clock.getMinute());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clock.getHour()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(clock.getMinute()));
        Log.e("TAG",calendar.getTimeInMillis()+"");
        Log.e("TAG",System.currentTimeMillis()+"");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (System.currentTimeMillis()>calendar.getTimeInMillis()+60000){
                //加24小时
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+86400000, sender);
            }else {
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            }
        }
        timeAdapter.notifyDataSetChanged();
        finish();
    }
}
