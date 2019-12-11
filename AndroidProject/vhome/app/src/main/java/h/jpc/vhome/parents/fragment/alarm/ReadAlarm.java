package h.jpc.vhome.parents.fragment.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import h.jpc.vhome.MyApp;
import h.jpc.vhome.R;
import h.jpc.vhome.children.fragment.historyAdapter.AlarmBean;
import h.jpc.vhome.user.entity.User;
import h.jpc.vhome.util.ConnectionUtil;

import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.list;
import static h.jpc.vhome.parents.fragment.alarm.AlarmActivity.timeAdapter;


public class ReadAlarm extends AppCompatActivity {
    private Calendar calendar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_alarm);
//        content = findViewById(R.id.content);
        calendar = Calendar.getInstance();
        //数据库
        list.clear();
        Intent intent = new Intent(ReadAlarm.this, CallAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                ReadAlarm.this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);


        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clock.getHour()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(clock.getMinute()));
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
    public void getAllAlarm(){
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);
        String phone = sp.getString("phone","");
        final String data = phone;
        new Thread(){
            @Override
            public void run() {
                String ip = (new MyApp()).getIp();
                try {
                    URL url = new URL("http://"+ip+":8080/vhome/showAllAlarm");
                    ConnectionUtil util = new ConnectionUtil();
                    //发送数据
                    HttpURLConnection connection = util.sendData(url,data);
                    //获取数据
                    final String data = util.getData(connection);
                    if(null!=data){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                sharedPreferences = getSharedPreferences("alarm",MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                String json = data;
                                //得到集合对应的具体类型
                                Type type = new TypeToken<List<AlarmBean>>(){}.getType();
                                List<AlarmBean> alarm = gson.fromJson(json,type);
                                for (int i=0;i<alarm.size();i++){
                                    String time = alarm.get(i).getAlarmTime();
                                    String content = alarm.get(i).getContent();
                                    String sendperson = alarm.get(i).getSendPersonId();
                                    String[] timer = time.split(":");
                                    Clock clock = new Clock();
                                    clock.setHour(timer[0]);editor.putString("hour"+i,timer[0]);
                                    clock.setMinute(timer[1]);editor.putString("minute"+i,timer[1]);
                                    clock.setContent(content);editor.putString("content"+i,content);
                                    clock.setSendPersonId(sendperson);editor.putString("sendperson"+i,sendperson);
                                    clock.setClockType(Clock.clock_open);editor.putString("clocktype"+i,Clock.clock_open+"");
                                    list.add(clock);editor.commit();
                                }
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
