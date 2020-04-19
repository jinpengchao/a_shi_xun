package com.vhome.vhome.parents.fragment.alarm;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.vhome.vhome.parents.fragment.HomeFragment;

public class AlarmService extends Service {
    private boolean flag;
    private Calendar calendar;
    public static String com;
    private int num = 0;
    private MyBinder binder = new MyBinder();
    public AlarmService() {
        Log.i("alarm", "Service构造方法");
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("alarm", "Service的onBind方法");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("alarm", "Service的onCreate方法+++");
        //启动线程实现每隔1s计1个数的功能
        new Thread(){
            @Override
            public void run() {
                while(!flag){
                    try {
                        calendar = Calendar.getInstance();
                        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);
                        final int second = calendar.get(Calendar.SECOND);
                        SharedPreferences sharedPreferences = getSharedPreferences("alarm",MODE_PRIVATE);
                        List<Integer> h = new ArrayList<>();
                        List<Integer> min = new ArrayList<>();
                        List<Integer> clocktype = new ArrayList<>();
                        for(int i =0;i<HomeFragment.size;i++){
                            h.add(Integer.parseInt(sharedPreferences.getString("hour"+i,"")));
                            min.add(Integer.parseInt(sharedPreferences.getString("minute"+i,"")));
                            clocktype.add(sharedPreferences.getInt("clocktype"+i,0));
                        }
                        Log.e("hour+minute",HomeFragment.size+"");
                        for (int i = 0; i<HomeFragment.size ;i++) {
                            Log.e("闹钟service循环的i=",i+"");
                            if (hour == h.get(i)) {
                                Log.e("hour:" + h.get(i), "小时---" + i);
                                if (minute == min.get(i)) {
                                    Log.e("minute：" + min.get(i), "分钟---" + i);
                                    if (clocktype.get(i) == 1) {
                                        Log.e("clocktype：" + clocktype.get(i), "状态---" + i);
                                        Intent intent = new Intent(AlarmService.this, CallAlarm.class);
                                        intent.putExtra("position", i);
                                        PendingIntent sender = PendingIntent.getBroadcast(
                                                AlarmService.this, 0, intent, 0);
                                        AlarmManager am;
                                        am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                                        }
                                    }
                                }
                            }
                        }
                        //设置免打扰时间
                        if(hour >= 22  && minute >= 00){
                            Log.e("alarm：sleep","您要休息了。我在这给您道句晚安！");
                            break;
                        }
                        Log.e("alarm：wait","请再等等吧！！！！！");
                        Thread.sleep(60000-second*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("alarm", "Service的onStartCommand方法");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = true;
        Log.i("alarm", "Service的onDestroy方法");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("alarm", "Service的onUnbind方法");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("alarm", "Service的onRebind方法");
    }
    class MyBinder extends Binder{
        public int getCount(){
            return num;
        }
    }
}
